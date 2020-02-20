package io.pixelplex.model.data.btc

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class BtcOutput(

    @SerializedName("address")
    val address: String,

    @SerializedName("is_coinbase")
    val isCoinBase: Boolean,

    @SerializedName("mint_block_height")
    val mintBlockHeight: BigInteger,

    @SerializedName("script")
    val script: String,

    @SerializedName("value")
    val value: BigInteger,

    @SerializedName("mint_index")
    val mintIndex: BigInteger,

    @SerializedName("mint_transaction_hash")
    val mintTansactionHash: String,

    @SerializedName("spent_block_height")
    val spentBlockHeight: BigInteger,

    @SerializedName("spent_transaction_hash")
    val spentTransactionHash: String,

    @SerializedName("spent_index")
    val spentInex: BigInteger,

    @SerializedName("sequence_number")
    val sequenceNumber: BigInteger,

    @SerializedName("mempool_time")
    val memPoolTime: String
)