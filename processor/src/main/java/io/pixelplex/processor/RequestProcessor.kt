package io.pixelplex.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.pixelplex.annotation.*
import io.pixelplex.model.generation.QueryType
import me.eugeniomarletti.kotlin.metadata.KotlinMetadataUtils
import me.eugeniomarletti.kotlin.metadata.shadow.name.FqName
import me.eugeniomarletti.kotlin.metadata.shadow.platform.JavaToKotlinClassMap
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RequestProcessor : KotlinAbstractProcessor(), KotlinMetadataUtils {

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val outputDir = generatedDir
        if (outputDir == null) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Cannot find generated output dir."
            )
            return false
        }

        val elements = getExecutableElements(
            roundEnv.getElementsAnnotatedWith(Get::class.java)
                    + roundEnv.getElementsAnnotatedWith(Post::class.java)
        )

        elements.forEach { (className, elements) ->
            generateCode(className, elements)
        }

        return true
    }

    private fun diagnostic(s: String) {
        messager.printMessage(
            Diagnostic.Kind.WARNING,
            s
        )
    }

    private fun getExecutableElements(elements: Set<Element>): Map<String, List<ExecutableElement>> {
        return elements.filter { element -> element.kind == ElementKind.METHOD && element is ExecutableElement }
            .map { it as ExecutableElement }.groupBy { getClassName(it) }
    }

    private fun generateCode(className: String, elements: List<ExecutableElement>) {

        var hasSuspend = false

        if (elements.isNotEmpty()) {
            val pack = elementUtils.getPackageOf(elements.first()).toString()

            val typeSpec = TypeSpec.classBuilder(className)

            val coinUrl = getClassAnnotationKey(elements.first())?.let {
                String.format(COINS_URL_FORMAT, it)
            } ?: ""

            val ctor = FunSpec.constructorBuilder().addParameter(
                API_CLIENT_PARAM_NAME,
                ClassName(CORE_PACKAGE, API_CLIENT_NAME)
            )

            typeSpec.addSuperinterface(
                ClassName(
                    pack,
                    getContractName(elements.first())
                )
            )

            typeSpec.primaryConstructor(ctor.build())

            val idProperty = PropertySpec
                .builder(
                    API_CLIENT_PARAM_NAME,
                    ClassName(CORE_PACKAGE, API_CLIENT_NAME),
                    KModifier.PRIVATE
                ).initializer(API_CLIENT_PARAM_NAME).build()

            typeSpec.addProperty(idProperty)

            elements.forEach { element ->
                if (element.parameters.map { getParamType(it) }.any { it.contains("kotlin.coroutines.Continuation") }) {
                    hasSuspend = true
                    generateSuspendMethod(element, coinUrl, typeSpec)

                } else {
                    generateMethod(element, coinUrl, typeSpec)
                }
            }

            val fileSpec = FileSpec.builder(pack, className)
                .addImport(CORE_PACKAGE, API_CLIENT_NAME)
                .addImport("io.pixelplex.model.generation", "QueryParameter", "QueryType", "RequestParameter")
                .addType(typeSpec.build())

            if (hasSuspend) {
                fileSpec.addImport("kotlin.coroutines", "resumeWithException", "suspendCoroutine")
            }

            fileSpec.addImport("okhttp3", "Callback", "Call", "Response")
            fileSpec.addImport("java.io", "IOException")
            fileSpec.addImport("io.pixelplex.cryptoapi_android_framework.support", "fromJson")
            fileSpec.addImport("io.pixelplex.model.exception", "ApiException")
            fileSpec.addImport("io.pixelplex.model.common", "ErrorResponse")

            val sourceFile = fileSpec.build()

            val kaptKotlinGeneratedDir = options[KAPT_KOTLIN_GENERATED_OPTION_NAME]

            val output = File(kaptKotlinGeneratedDir)
            sourceFile.writeTo(output)
        }
    }

    private fun generateMethod(
        element: ExecutableElement,
        coinUrl: String,
        typeSpec: TypeSpec.Builder
    ) {

        val funcBuilder = FunSpec.builder(element.simpleName.toString())
            .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
            .addParameters(element.parameters.map {
                ParameterSpec(
                    it.simpleName.toString(),
                    it.asType().asTypeName().javaToKotlinType(),
                    emptyList<KModifier>()
                )
            })

        val errorCalback: VariableElement? = findErrorCallback(element)
        val successCalback: VariableElement? = findSuccessCallback(element)

        if (errorCalback == null || successCalback == null) {
            throw java.lang.IllegalStateException("Method should be contain Annotated Error and Success Callbacks")
        }

        funcBuilder.addCode(
            """val callback = object: Callback {
                   override fun onFailure(call: Call, e: IOException) {
                       ${errorCalback.simpleName}(ApiException.create(e))
                    }
                    override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                         try {
                            ${successCalback.simpleName}(fromJson(response.body?.string()!!))
                         } catch (e: Exception){
                            ${errorCalback.simpleName}(ApiException.create(e))
                         }
                    } else {
                        ${errorCalback.simpleName}(ApiException.create(fromJson<ErrorResponse>(response.body?.string()!!)))
                    }
                    }
                }
                        
        """.trimIndent()
        )

        if (element.parameters.mapNotNull { getQueryType(it) }.any()) {
            funcBuilder.addCode(
                codeSnippetLine("val queryParams = ArrayList<QueryParameter<*>>()")
            )

            element.parameters.filter { getParamName(it) != null && getQueryType(it) != null }
                .forEach {
                    funcBuilder.addCode(
                        codeSnippetLine(
                            "queryParams.add(QueryParameter<${getParamType(it)}>(\"${getParamName(
                                it
                            )}\", RequestParameter<${getParamType(it)}>(${it.simpleName}), QueryType.${getQueryType(
                                it
                            )!!.name}))"
                        )
                    )
                }
            funcBuilder.addCode(
                codeSnippetLine(
                    "${API_CLIENT_PARAM_NAME}.callApi(path = \"${coinUrl + getPath(element)}\", callback = callback, params = queryParams)"
                )
            )
        } else {
            funcBuilder.addCode(
                codeSnippetLine(
                    "${API_CLIENT_PARAM_NAME}.callApi(path = \"${coinUrl + getPath(element)}\", callback = callback)"
                )
            )
        }

        typeSpec.addFunction(funcBuilder.build())
    }

    private fun findErrorCallback(element: ExecutableElement): VariableElement? {
        return element.parameters.first { it.getAnnotation(CallbackError::class.java) != null }
    }

    private fun findSuccessCallback(element: ExecutableElement): VariableElement? {
        return element.parameters.first { it.getAnnotation(CallbackSuccess::class.java) != null }
    }

    private fun generateSuspendMethod(
        element: ExecutableElement,
        coinUrl: String,
        typeSpec: TypeSpec.Builder
    ) {

        val funcBuilder = FunSpec.builder(element.simpleName.toString())
            .addModifiers(KModifier.SUSPEND, KModifier.OVERRIDE)
            .addParameters(element.parameters.filter { !getParamType(it).contains("kotlin.coroutines.Continuation") }
                .map {

                    ParameterSpec(
                        it.simpleName.toString(),
                        it.asType().asTypeName().javaToKotlinType(),
                        emptyList<KModifier>()
                    )
                })

        if (funcBuilder.parameters.isNotEmpty()) {
            funcBuilder.addCode(
                """return suspendCoroutine<${getSuspendReturnType(element)}> {

                """
            )
        } else {
            funcBuilder.addCode(
                """return suspendCoroutine<${getSuspendReturnType(element)}> {
                
                """
            )
        }


        funcBuilder.addCode(
            """val callback = object: Callback {
                   override fun onFailure(call: Call, e: IOException) {
                       it.resumeWithException(ApiException.create(e))
                    }

                    override fun onResponse(call: Call, response: Response) {
                    
                        if(response.isSuccessful){
                            try{
                                it.resumeWith(Result.success(fromJson(response.body?.string()!!)))
                            } catch(e: Exception) {
                                it.resumeWithException(ApiException.create(e))
                            }
                        } else {
                            it.resumeWithException(ApiException.create(fromJson<ErrorResponse>(response.body?.string()!!)))
                        }
                    }
                }
                        
        """.trimIndent()
        )

        if (element.parameters.mapNotNull { getQueryType(it) }.any()) {
            funcBuilder.addCode(
                codeSnippetLine("val queryParams = ArrayList<QueryParameter<*>>()")
            )

            element.parameters.filter { getParamName(it) != null && getQueryType(it) != null }
                .forEach {
                    funcBuilder.addCode(
                        codeSnippetLine(
                            "queryParams.add(QueryParameter<${getParamType(it)}>(\"${getParamName(
                                it
                            )}\", RequestParameter<${getParamType(it)}>(${it.simpleName}), QueryType.${getQueryType(
                                it
                            )!!.name}))"
                        )
                    )
                }
            funcBuilder.addCode(
                codeSnippetLine(
                    "${API_CLIENT_PARAM_NAME}.callApi(path = \"${coinUrl + getPath(element)}\", callback = callback, params = queryParams)"
                )
            )
        } else {
            funcBuilder.addCode(
                codeSnippetLine(
                    "${API_CLIENT_PARAM_NAME}.callApi(path = \"${coinUrl + getPath(element)}\", callback = callback)"
                )
            )
        }

        funcBuilder.addCode(codeSnippetLine("}"))
        typeSpec.addFunction(funcBuilder.build())
    }

    private fun getSuspendReturnType(element: ExecutableElement): String {
        with(element.parameters.last().asType().asTypeName().javaToKotlinType() as ParameterizedTypeName) {
            return (this.typeArguments[0].javaToKotlinType() as WildcardTypeName).inTypes[0].javaToKotlinType()
                .toString()
        }
    }

    private fun codeSnippetLine(code: String): String {
        return """$code 

        """.trimIndent()
    }

    private fun getParamName(param: VariableElement): String? {
        param.getAnnotation(Query::class.java)?.name?.let { return it }
        param.getAnnotation(Path::class.java)?.name?.let { return it }
        param.getAnnotation(Body::class.java)?.let { return param.simpleName.toString() }
        return null
    }

    private fun getParamType(param: VariableElement): String {
        return param.asType().asTypeName().javaToKotlinType().toString()
    }

    private fun getQueryType(param: VariableElement): QueryType? {
        param.getAnnotation(Query::class.java)?.name?.let { return QueryType.QUERY }
        param.getAnnotation(Path::class.java)?.name?.let { return QueryType.PATH }
        param.getAnnotation(Body::class.java)?.let { return QueryType.BODY }
        return null
    }

    private fun getClassAnnotationKey(element: ExecutableElement): String? {
        val declaringClass = element.enclosingElement as TypeElement
        return declaringClass.getAnnotation(Coin::class.java)?.name
    }

    private fun getClassName(element: ExecutableElement): String {
        val declaringClass = element.enclosingElement as TypeElement
        return declaringClass.asClassName().simpleName + "Impl"
    }

    private fun getContractName(element: ExecutableElement): String {
        val declaringClass = element.enclosingElement as TypeElement
        return declaringClass.asClassName().simpleName
    }

    private fun getPath(element: ExecutableElement): String {
        element.getAnnotation(Get::class.java)?.path?.let { return it }
        element.getAnnotation(Post::class.java)?.path?.let { return it }
        throw IllegalArgumentException("method ${element.simpleName} must be annotated by GET or POST")
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(Get::class.java.canonicalName, Post::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        private const val CORE_PACKAGE = "io.pixelplex.cryptoapi_android_framework.core"
        private const val API_CLIENT_NAME = "CryptoApi"
        private const val COINS_URL_FORMAT = "coins/%s/"
        private const val API_CLIENT_PARAM_NAME = "apiClient"
    }

    private fun TypeName.javaToKotlinType(): TypeName {

        return when (this) {
            is ParameterizedTypeName -> {
                (rawType.javaToKotlinType() as ClassName).parameterizedBy(
                    *typeArguments.map {
                        it.javaToKotlinType()
                    }.toTypedArray()
                )
            }
            is WildcardTypeName -> {
                val type =
                    if (inTypes.isNotEmpty()) WildcardTypeName.consumerOf(inTypes[0].javaToKotlinType())
                    else WildcardTypeName.producerOf(outTypes[0].javaToKotlinType())
                type
            }

            else -> {
                val className = JavaToKotlinClassMap
                    .mapJavaToKotlin(FqName(toString()))?.asSingleFqName()?.asString()
                if (className == null) {
                    this
                } else {
                    ClassName.bestGuess(className)
                }
            }
        }
    }
}