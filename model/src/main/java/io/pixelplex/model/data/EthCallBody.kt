package io.pixelplex.model.data

interface EthCallBody

abstract class SkipLimitEthCallBody : EthCallBody {
    abstract val skip: Int
    abstract val limit: Int
}