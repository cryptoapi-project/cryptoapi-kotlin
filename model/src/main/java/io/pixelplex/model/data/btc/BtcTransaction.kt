package io.pixelplex.model.data.btc

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class BtcTransaction(

    @SerializedName("block_height")
    val blockHeight: BigInteger,

    @SerializedName("block_hash")
    val blockHash: String,

    @SerializedName("block_time")
    val blockTime: String,

    @SerializedName("mempool_time")
    val memPoolTime: String?,

    @SerializedName("fee")
    val fee: BigInteger,

    @SerializedName("size")
    val size: BigInteger,

    @SerializedName("transaction_index")
    val txIndex: BigInteger,

    @SerializedName("n_lock_time")
    val nLockTime: BigInteger,

    @SerializedName("value")
    val value: String,

    @SerializedName("hash")
    val hash: String,

    @SerializedName("input_count")
    val inputCount: String,

    @SerializedName("output_count")
    val outputCount: String,

    @SerializedName("inputs")
    val inputs: List<Input>,

    @SerializedName("outputs")
    val outputs: List<Output>
) {

    data class Input(

        @SerializedName("address")
        val address: String,

        @SerializedName("previous_transaction_hash")
        val prevTransactionHash: String,

        @SerializedName("output_index")
        val outputIndex: BigInteger,

        @SerializedName("sequence_number")
        val sequenceNumber: BigInteger,

        @SerializedName("script")
        val script: String
    )

    data class Output(

        @SerializedName("address")
        val address: String,

        @SerializedName("satoshis")
        val satoshis: BigInteger,

        @SerializedName("script")
        val script: String
    )
}