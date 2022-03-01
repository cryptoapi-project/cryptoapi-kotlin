package io.pixelplex.cryptoapi.example.models

class RLPList(override var rlpData: ByteArray? = null) : ArrayList<RLPElement>(), RLPElement

interface RLPElement : java.io.Serializable {

    val rlpData: ByteArray?
}

class RLPItem(data: ByteArray) : RLPElement {

    override var rlpData: ByteArray? = data
        get() = if (field?.size == 0) null else field
}

