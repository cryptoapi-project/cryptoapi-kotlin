package io.pixelplex.cryptoapi.example.model

import io.pixelplex.cryptoapi.example.model.Parameter.KECCAK_256
import io.pixelplex.cryptoapi.example.models.EncodingContext
import io.pixelplex.cryptoapi.example.models.INPUT_SLICE_SIZE
import io.pixelplex.cryptoapi.example.models.InputValue
import org.spongycastle.util.encoders.Hex


class ContractInputEncoder {

    private lateinit var keccak: Keccak

    /**
     * Encodes contract method [methodName] with all parameters [params]
     */
    fun encode(methodName: String, params: List<InputValue> = listOf()): String {
        keccak = Keccak()

        var encodedMethodName = methodName

        encodedMethodName += params.asSequence().map { parameter -> parameter.type.name }
            .joinToString(separator = ",", prefix = "(", postfix = ")")

        val methodHex = Hex.toHexString(encodedMethodName.toByteArray())
        val hashMethod = keccak.getHash(methodHex, KECCAK_256).substring(0, 8)

        return hashMethod + encodeArguments(params)
    }

    /**
     * Encodes all specified contract methods parameters [params]
     */
    fun encodeArguments(params: List<InputValue> = listOf()): String {
        val context = ContractInputEncodingContext(params.size)

        var abiParams = ""

        params.forEach { parameter ->
            parameter.type.encodingContext = context
            abiParams += parameter.type.encode(parameter.value)
        }

        return abiParams + context.dynamicParams
    }

    /**
     * Implementation of [EncodingContext] for [ContractInputEncoder] encoding process
     */
    class ContractInputEncodingContext(
        override val paramsCount: Int
    ) : EncodingContext {

        override var dynamicParametersOffset: Long = (paramsCount * INPUT_SLICE_SIZE).toLong()

        var dynamicParams: String = ""

        override fun appendDynamicDataPart(value: String) {
            dynamicParams += value
        }
    }

}
