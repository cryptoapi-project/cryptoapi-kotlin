package io.pixelplex.cryptoapi_android_framework.generated

import io.pixelplex.cryptoapi_android_framework.support.future.FutureTask
import io.pixelplex.model.data.*

object TestValues {

    const val INVALID_ADDRESS_ERROR = 422
    const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
    const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"
    const val HASH = "0x2ebfff2a09f677229dced9a9d25500694f9d63e1a4cc7bf65cc635272380ac02"
    const val CONTRACT_ADDRESS = "0xf36c145eff2771ea22ece5fd87392fc8eeae719c"
    const val TX =
        "0xf86e8386ca038602bba7f5220083632ea0941de29f644d555fe9cc3241e1083de0868f959bfa8545d964b800801ca04ef1f13c58af9a9ac4be66b838a238b24db798d585d882865637fdc35bdc49c4a04b7d1dfc3d9672080347a0d3559628f5f757bd6f6a005d1c4f7cdccce020ea02"


    val testEstimatedGasFuture = FutureTask<Any>()

    val estimatedGas = EthEstimatedGasCallBody(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        value = "10"
    )

    val badEstimatedGas = EthEstimatedGasCallBody(
        from = "0x141d59",
        to = ETH_ADDRESS_2,
        value = "10"
    )

    val ethAddresses = EthTypedParams(
        ETH_ADDRESS_1,
        ETH_ADDRESS_2
    )
    val badEthAddresses =
        EthTypedParams(ETH_ADDRESS_1, "0xb0202eBbF797Dd61A")

    val ethTransfer = EthTransferCallBody(
        typedParams = ethAddresses,
        skip = 0,
        limit = 1,
        positive = "positivestring"
    )

    val badEthTransfer = EthTransferCallBody(
        typedParams = badEthAddresses,
        skip = 0,
        limit = 1,
        positive = "positivestring"
    )

    val ethTransactionExternal =
        EthTransactionExternalCallBody(
            typedParams = ethAddresses,
            skip = 0,
            limit = 1
        )

    val badEthTransactionExternal =
        EthTransactionExternalCallBody(
            typedParams = badEthAddresses,
            skip = 0,
            limit = 1
        )

    val ethTransaction = EthTransactionCallBody(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        skip = 0,
        limit = 3
    )

    val badEthTransaction = EthTransactionCallBody(
        from = "0x141d5937C7b",
        to = ETH_ADDRESS_2,
        skip = 0,
        limit = 3
    )

    val contractCallBody = EthContractCallBody(
        sender = ETH_ADDRESS_1,
        amount = 0,
        bytecode = "0x899426490000000000000000000000000000000000000000000000000000000000000001"
    )

    val badContractCallBody =
        EthContractCallBody(
            sender = "0x141d593",
            amount = 0,
            bytecode = "0x899426490000000000000000000000000000000000000000000000000000000000000001"
        )

    val ethTransactionRawBody =
        EthTransactionRawCallBody(
            tx = TX
        )

    val badEthTransactionRawBody =
        EthTransactionRawCallBody(
            tx = "0xf86e8386ca0"
        )

    val ethTokensBalancesBody =
        EthTokenBalanceCallBody(
            skip = 0,
            limit = 3,
            address = ETH_ADDRESS_1
        )

    val badEthTokensBalancesBody =
        EthTokenBalanceCallBody(
            skip = 0,
            limit = 3,
            address = "0x141d5937C7"
        )

    val ethTokensTransfersBody =
        EthTokenTransferCallBody(
            skip = 0,
            limit = 3,
            typedParams = EthTypedParams(
                ETH_ADDRESS_1,
                ETH_ADDRESS_2
            ),
            token = CONTRACT_ADDRESS
        )

    val badEthTokensTransfersBody =
        EthTokenTransferCallBody(
            skip = 0,
            limit = 3,
            typedParams = EthTypedParams("0xb0202eBbF79"),
            token = CONTRACT_ADDRESS
        )

    val ethTokensSearchBody =
        EthTokenSearchCallBody(
            skip = 0,
            limit = 3,
            types = EthTypedParams("ERC20"),
            query = "r"
        )

    val badEthTokensSearchBody =
        EthTokenSearchCallBody(
            skip = 0,
            limit = 3,
            types = EthTypedParams(ETH_ADDRESS_1, ETH_ADDRESS_2),
            query = "r"
        )
}