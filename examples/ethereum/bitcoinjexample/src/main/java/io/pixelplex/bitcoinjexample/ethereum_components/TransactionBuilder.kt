package io.pixelplex.bitcoinjexample.ethereum_components

import org.spongycastle.util.encoders.Hex
import java.math.BigInteger

class TransactionBuilder {

    fun createSignedTransaction(
        nonce: BigInteger,
        gasPrice: BigInteger,
        gasLimit: BigInteger,
        receiverAddress: String,
        value: BigInteger,
        privateKey: ByteArray,
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
                value.toBytes(),
                data?.let { Hex.decode(it) }
            )

        val senderKey = EthECKey.fromPrivate(privateKey)
        transaction.sign(senderKey)

        return "0x${Hex.toHexString(transaction.encoded)}"
    }

    companion object {
        private const val PREFIX = "0x"
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

    /**
     * Omitting sign indication byte.
     * <br></br><br></br>
     * Instead of [org.spongycastle.util.BigIntegers.asUnsignedByteArray]
     * <br></br>we use this custom method to avoid an empty array in case of BigInteger.ZERO
     *
     * @param value - any big integer number. A `null`-value will return `null`
     * @return A byte array without a leading zero byte if present in the signed encoding.
     * BigInteger.ZERO will return an array with length 1 and byte-value 0.
     */
    fun BigInteger?.toBytes(): ByteArray? {
        if (this == null)
            return null

        var data = this.toByteArray()

        if (data.size != 1 && data[0].toInt() == 0) {
            val tmp = ByteArray(data.size - 1)
            System.arraycopy(data, 1, tmp, 0, tmp.size)
            data = tmp
        }
        return data
    }

}