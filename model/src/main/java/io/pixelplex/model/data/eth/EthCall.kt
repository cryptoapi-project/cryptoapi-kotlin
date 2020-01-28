package io.pixelplex.model.data.eth

/**
 * Segregates models, associated with some ETH bodies calls
 *
 * @author Sergey Krupenich
 */
interface EthCallBody

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests
 *
 * @author Sergey Krupenich
 */
abstract class SkipLimitEthCallBody : EthCallBody {
    abstract val skip: Int
    abstract val limit: Int
}