package io.pixelplex.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.pixelplex.annotation.*
import me.eugeniomarletti.kotlin.metadata.KotlinMetadataUtils
import me.eugeniomarletti.kotlin.metadata.shadow.name.FqName
import me.eugeniomarletti.kotlin.metadata.shadow.platform.JavaToKotlinClassMap
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import java.lang.IllegalArgumentException
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.DeclaredType
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
            roundEnv.getElementsAnnotatedWith(GET::class.java)
        )
        generateCode(elements)

        return true
    }

    private fun getExecutableElements(elements: Set<Element>): List<ExecutableElement> {
        return elements.filter { element -> element.kind == ElementKind.METHOD && element is ExecutableElement }
            .map { it as ExecutableElement }
    }

    private fun generateCode(elements: List<ExecutableElement>) {

        if (elements.isNotEmpty()) {
            val pack = elementUtils.getPackageOf(elements.first()).toString()

            val className = getClassName(elements.first())

            val typeSpec = TypeSpec.classBuilder(className)

            val coinUrl = String.format(COINS_URL_FORMAT, getClassAnnotationKey(elements.first()))

            val ctor = FunSpec.constructorBuilder().addParameter(
                "apiCoins",
                ClassName(CORE_PACKAGE, API_CLIENT_NAME)
            )

            typeSpec.addSuperinterface(
                ClassName(
                    "io.pixelplex.cryptoapi_android_framework",
                    getContractName(elements.first())
                )
            ) //TODO REFACTOR

            typeSpec.primaryConstructor(ctor.build())

            val idProperty = PropertySpec
                .builder(
                    "apiCoins",
                    ClassName(CORE_PACKAGE, API_CLIENT_NAME),
                    KModifier.PRIVATE
                ).initializer("apiCoins").build()

            typeSpec.addProperty(idProperty)


            elements.forEach { element ->
                val funcBuilder = FunSpec.builder(element.simpleName.toString())
                    .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                    .addParameters(element.parameters.map {
                        ParameterSpec(
                            it.simpleName.toString(),
                            it.asType().asTypeName().javaToKotlinType(),
                            it.modifiers as Iterable<KModifier>
                        )
                    })
                funcBuilder.addCode(
                    """
                    val queryParams = ArrayList<QueryParameter>()
                    
                """.trimIndent()
                )

                element.parameters.filter { getParamName(it) != null }.forEach {
                    funcBuilder.addCode(
                        """
                    queryParams.add(QueryParameter("${getParamName(it)}",${it.simpleName}, QueryType.PATH))
                    
                """.trimIndent()
                    )
                }

                funcBuilder.addCode(
                    """
                    apiCoins.callApi(path = "${coinUrl + getPath(element)}", callback = callback, params = queryParams)
                    
                """.trimIndent()
                )
                //  .returns(element.returnType.asTypeName())

                typeSpec.addFunction(funcBuilder.build())
            }

            val file = FileSpec.builder(pack, className)
                .addImport(CORE_PACKAGE, API_CLIENT_NAME)
                .addImport("io.pixelplex.model", "QueryParameter", "QueryType")
                .addType(typeSpec.build())


            val sourceFile = file.build()

            val kaptKotlinGeneratedDir = options[KAPT_KOTLIN_GENERATED_OPTION_NAME]

            val output = File(kaptKotlinGeneratedDir)

            sourceFile.writeTo(output)
        }
    }

    private fun getParamName(param: VariableElement): String? {
        param.getAnnotation(QUERY::class.java)?.name?.let { return it }
        param.getAnnotation(PATH::class.java)?.name?.let { return it }
        param.getAnnotation(BODY::class.java)?.let { return param.simpleName.toString() }
        return null
    }

    private fun getClassAnnotationKey(element: ExecutableElement): String {
        val declaringClass = element.enclosingElement as TypeElement
        val annotation = declaringClass.getAnnotation(COIN::class.java)
        return annotation.name
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
        var path = element.getAnnotation(GET::class.java)?.path
        path?.let { return it }

        path = element.getAnnotation(POST::class.java)?.path
        path?.let { return it }

        throw IllegalArgumentException("method ${element.simpleName} must be annotated by GET or POST")
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(GET::class.java.canonicalName, POST::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        private const val CORE_PACKAGE = "io.pixelplex.cryptoapi_android_framework.core"
        private const val API_CLIENT_NAME = "CryptoApi"
        private const val COINS_URL_FORMAT = "coins/%s/"
    }
}

private fun TypeName.javaToKotlinType(): TypeName = if (this is ParameterizedTypeName) {
    (rawType.javaToKotlinType() as ClassName).parameterizedBy(
        *typeArguments.map { it.javaToKotlinType() }.toTypedArray()
    )
} else {
    val className = JavaToKotlinClassMap
        .mapJavaToKotlin(FqName(toString()))?.asSingleFqName()?.asString()
    if (className == null) this
    else ClassName.bestGuess(className)
}
