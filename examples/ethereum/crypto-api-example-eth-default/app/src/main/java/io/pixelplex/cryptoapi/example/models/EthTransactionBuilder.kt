package io.pixelplex.cryptoapi.example.models

import io.pixelplex.cryptoapi.example.util.toBytes
import io.pixelplex.cryptoapi.example.util.withPrefix
import org.spongycastle.util.encoders.Hex
import java.math.BigInteger

object EthTransactionBuilder {

    private const val PREFIX = "0x"

    fun createSignedTransaction(
        nonce: BigInteger,
        gasPrice: BigInteger,
        gasLimit: BigInteger,
        receiverAddress: String,
        value: BigInteger?,
        privateKey: ByteArray,
        chainId: Int,
        data: String? = null
    ): String {
        val addressWithoutPrefix = receiverAddress.removePrefix(PREFIX)
        val addressBytes = Hex.decode(addressWithoutPrefix)

        val transaction =
            EthTransaction(
                nonce.toBytes(),
                gasPrice.toBytes(),
                gasLimit.toBytes(),
                addressBytes,
                value?.toBytes() ?: EthTransaction.EMPTY_BYTE_ARRAY,
                data?.let { Hex.decode(it) },
                chainId
            )

        val senderKey = EthECKey.fromPrivate(privateKey)
        transaction.sign(senderKey)

        return Hex.toHexString(transaction.encoded).withPrefix(PREFIX)
    }

    fun getContractBytecode(toAddress: String, amountWithWei: BigInteger): String {
        val byteCode = ContractInputEncoder().encode(
            "transfer", listOf(
                InputValue(
                    EthContractAddressInputValueType("address"),
                    toAddress
                ), InputValue(
                    NumberInputValueType("uint256"), amountWithWei.toString()
                )
            )
        )
        return byteCode
    }


}