package io.pixelplex.mobile.cryptoapi.app.coins.eth

import io.pixelplex.mobile.cryptoapi.model.data.eth.*

object TestValues {

    const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
    const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"
    const val HASH = "0x1ed7742bd59a7f94dac9eaac8ae4db683d365e6ef8a1609abe167adb6cbd20ed"
    const val CONTRACT_ADDRESS = "0x30820824cadaf67d19989263bcabe03fc85d0321"
    const val TX =
        "0xf86e8386ca038602bba7f5220083632ea0941de29f644d555fe9cc3241e1083de0868f959bfa8545d964b800801ca04ef1f13c58af9a9ac4be66b838a238b24db798d585d882865637fdc35bdc49c4a04b7d1dfc3d9672080347a0d3559628f5f757bd6f6a005d1c4f7cdccce020ea02"

    val estimatedGas =
        EthEstimatedGasCall(
            from = ETH_ADDRESS_1,
            to = ETH_ADDRESS_2,
            value = "10"
        )

    val badEstimatedGas =
        EthEstimatedGasCall(
            from = "0x141d59",
            to = ETH_ADDRESS_2,
            value = "10"
        )

    val ethAddresses =
        EthTypedParams(
            ETH_ADDRESS_1,
            ETH_ADDRESS_2
        )
    val badEthAddresses =
        EthTypedParams(
            ETH_ADDRESS_1,
            "0xb0202eBbF797Dd61A"
        )

    val ethTransfer =
        EthTransferCall(
            typedParams = ethAddresses,
            skip = 0,
            limit = 1,
            positive = "positivestring"
        )

    val badEthTransfer =
        EthTransferCall(
            typedParams = badEthAddresses,
            skip = 0,
            limit = 1,
            positive = "positivestring"
        )

    val ethTransactionExternal =
        EthTransactionExternalCall(
            typedParams = ethAddresses,
            skip = 0,
            limit = 1
        )

    val badEthTransactionExternal =
        EthTransactionExternalCall(
            typedParams = badEthAddresses,
            skip = 0,
            limit = 1
        )

    val ethTransaction =
        EthTransactionCall(
            from = ETH_ADDRESS_1,
            to = ETH_ADDRESS_2,
            skip = 0,
            limit = 3
        )

    val badEthTransaction =
        EthTransactionCall(
            from = "0x141d5937C7b",
            to = ETH_ADDRESS_2,
            skip = 0,
            limit = 3
        )

    val contractCallBody =
        EthContractCall(
            sender = ETH_ADDRESS_1,
            amount = 0,
            bytecode = "0x899426490000000000000000000000000000000000000000000000000000000000000001"
        )

    val ethTransactionRawBody =
        EthTransactionRawCall(
            tx = TX
        )

    val badEthTransactionRawBody =
        EthTransactionRawCall(
            tx = "0xf86e8386ca0"
        )

    val ethTokensBalancesBody =
        EthTokenBalanceCall(
            skip = 0,
            limit = 3,
            address = ETH_ADDRESS_1
        )

    val badEthTokensBalancesBody =
        EthTokenBalanceCall(
            skip = 0,
            limit = 3,
            address = "0x141d5937C7"
        )

    val ethTokensTransfersBody =
        EthTokenTransferCall(
            skip = 0,
            limit = 3,
            typedParams = EthTypedParams(
                ETH_ADDRESS_1,
                ETH_ADDRESS_2
            ),
            token = CONTRACT_ADDRESS
        )

    val badEthTokensTransfersBody =
        EthTokenTransferCall(
            skip = 0,
            limit = 3,
            typedParams = EthTypedParams(
                "0xb0202eBbF79"
            ),
            token = CONTRACT_ADDRESS
        )

    val ethTokensSearchBody =
        EthTokenSearchCall(
            skip = 0,
            limit = 3,
            types = EthTypedParams(
                "ERC20"
            ),
            query = "r"
        )

    val badEthTokensSearchBody =
        EthTokenSearchCall(
            skip = 0,
            limit = 3,
            types = EthTypedParams(
                ETH_ADDRESS_1,
                ETH_ADDRESS_2
            ),
            query = "r"
        )

    // TODO: may be expired
    const val FIREBASE_TOKEN = "eNC9lpjcxQM:APA91bHVC4o-vN_G_sO70Fdag0APLKwNWcAj-geZ3lBquAYjGwdhmm6swDTNbwU8tOJjdBdJSXHWAx-6pIMA5apoJViywG_LhbxQSVXUI7ZVT3CKs6UWg-k-65LNOSk-gEa3QjTswHjf"
}