package io.pixelplex.mobile.cryptoapi.model.data.btc

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class BtcBlock(

    @SerializedName("height")
    val height: BigInteger,

    @SerializedName("hash")
    val hash: String,

    @SerializedName("bits")
    val bits: BigInteger,

    @SerializedName("time")
    val time: String,

    @SerializedName("merkle_root")
    val merkleRoot: String,

    @SerializedName("nonce")
    val nonce: BigInteger,

    @SerializedName("size")
    val size: BigInteger,

    @SerializedName("version")
    val version: BigInteger,

    @SerializedName("previous_block_hash")
    val prevBlockHash: String,

    @SerializedName("next_block_hash")
    val nextBlockHash: String,

    @SerializedName("reward")
    val reward: BigInteger,

    @SerializedName("count_transactions")
    val transactionCount: BigInteger
)