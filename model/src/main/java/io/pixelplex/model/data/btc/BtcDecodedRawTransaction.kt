package io.pixelplex.model.data.btc

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class BtcDecodedRawTransaction(

    @SerializedName("hash")
    val hash: String,

    @SerializedName("version")
    val version: BigInteger,

    @SerializedName("n_lock_time")
    val nLockTime: String,

    @SerializedName("inputs")
    val inputs: List<Input>,

    @SerializedName("outputs")
    val outputs: List<Output>
) {
    data class Input(

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

        @SerializedName("script_pub_key")
        val scriptPubKey: String,

        @SerializedName("satoshis")
        val satoshis: BigInteger,

        @SerializedName("script")
        val script: String
    )
}