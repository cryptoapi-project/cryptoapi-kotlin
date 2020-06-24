package io.pixelplex.mobile.cryptoapi.app.coins.eth

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.app.BuildConfig
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.model.data.push.EthTokenFirebaseToken
import io.pixelplex.mobile.cryptoapi.model.data.push.FirebaseToken
import io.pixelplex.mobile.cryptoapi.model.data.push.NotificationType
import io.pixelplex.mobile.cryptoapi.model.data.push.convertNotificationTypes
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AsyncApiTest {
    private val apiClient = CryptoApiFramework.getInstance(
        CryptoApiConfiguration(
            url = CryptoApi.URL.TESTNET,
            authorizationToken = BuildConfig.CRYPTO_API_KEY
        )
    ).ethereumAsyncApi

    @Test
    fun estimateGas() = runBlocking {
        try {
            apiClient.estimateGas(TestValues.estimatedGas).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun estimateGasFail() = runBlocking {
        try {
            apiClient.estimateGas(TestValues.badEstimatedGas).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getNetwork() = runBlocking {
        try {
            apiClient.getNetwork().let { resp ->
                Assert.assertTrue(resp.lastBlock > 0)
            }

        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBalances() = runBlocking {
        try {
            apiClient.getBalances(TestValues.ethAddresses.getList()).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBalancesFail() = runBlocking {
        try {
            apiClient.getBalances(TestValues.badEthAddresses.getList()).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthInfo() = runBlocking {

        try {
            apiClient.getInfo(addresses = TestValues.ethAddresses.getList()).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthInfoFail() = runBlocking {
        try {
            apiClient.getInfo(addresses = listOf("badaddress")).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransfers() = runBlocking {
        try {
            apiClient.getTransfers(
                TestValues.ethTransfer.typedParams.getList(),
                true
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransfersFail() = runBlocking {
        try {
            apiClient.getTransfers(
                TestValues.badEthTransfer.typedParams.getList(),
                true
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransactionsExternal() = runBlocking {
        try {
            apiClient.getExternalTransactions(
                TestValues.ethTransactionExternal2.typedParams.getList()
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransactionsExternalFail() = runBlocking {
        try {
            apiClient.getExternalTransactions(
                addresses = listOf("badaddress")
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransactions() = runBlocking {
        try {
            apiClient.getTransactions(
                TestValues.ethTransaction.from,
                TestValues.ethTransaction.to
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransactionsFail() = runBlocking {
        try {
            apiClient.getTransactions(
                TestValues.badEthTransaction.from,
                TestValues.badEthTransaction.to
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthTransactionsByHash() = runBlocking {
        try {
            apiClient.getTransaction(
                TestValues.HASH
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthTransactionsByHashFail() = runBlocking {
        try {
            apiClient.getTransaction(
                "sdfsefgwerfer"
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun getEthContractsInfo() = runBlocking {
        try {
            apiClient.getContractInfo(
                TestValues.CONTRACT_ADDRESS
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getEthContractsInfoFail() = runBlocking {
        try {
            apiClient.getContractInfo(
                "sfdvsefefv"
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun callEthContract() = runBlocking {
        try {
            apiClient.callContract(
                TestValues.CONTRACT_ADDRESS,
                TestValues.contractCallBody
            ).let { resp ->
                Assert.assertTrue(resp.isNotEmpty())
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun callEthContractFail() = runBlocking {
        try {
            apiClient.callContract(
                "segsefwerfwerf",
                TestValues.contractCallBody
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun transactionsRawSendFail() = runBlocking {
        try {
            apiClient.sendRawTransaction(
                TestValues.ethTransactionRawBody
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun transactionsRawDecode() = runBlocking {
        try {
            apiClient.decodeRawTransaction(
                TestValues.ethTransactionRawBody
            ).let { resp ->
                Assert.assertTrue(resp.v!! > 0)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun transactionsRawDecodeFail() = runBlocking {
        try {
            apiClient.decodeRawTransaction(
                TestValues.badEthTransactionRawBody
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun tokensBalances() = runBlocking {
        try {
            apiClient.getTokenBalances(
                listOf(TestValues.ethTokensBalancesBody.address)
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokensTransfers() = runBlocking {
        try {
            apiClient.getTokenTransfers(
                TestValues.ethTokensTransfersBody.token,
                TestValues.ethTokensTransfersBody.typedParams.getList()
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokensTransfersFail() = runBlocking {
        try {
            apiClient.getTokenTransfers(
                TestValues.badEthTokensTransfersBody.token,
                TestValues.badEthTokensTransfersBody.typedParams.getList()
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
        }
    }

    @Test
    fun tokenInfo() = runBlocking {
        try {
            apiClient.getTokenInfo(
                TestValues.CONTRACT_ADDRESS
            ).let { resp ->
                Assert.assertNotNull(resp)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokenInfoFail() = runBlocking {
        try {
            apiClient.getTokenInfo(
                "bad_address"
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {

        }
    }

    @Test
    fun tokensSearch() = runBlocking {
        //TODO REMOVE "r" and "3"
        try {
            apiClient.searchTokens(
                query = "r",
                limit = 3,
                types = TestValues.ethTokensSearchBody.types.getList()
            ).let { resp ->
                Assert.assertTrue(resp.items.isNotEmpty())
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun tokensSearchFail() = runBlocking {
        //TODO REMOVE "r" and "3"
        try {
            apiClient.searchTokens(
                query = "r",
                limit = 3,
                types = TestValues.badEthTokensSearchBody.types.getList()
            ).let { resp ->
                Assert.fail()
            }
        } catch (e: Exception) {
            Assert.assertTrue(true)
        }
    }

// TODO: uncomment and change tokens run the tests below
//    @Test
//    fun subscribe() = runBlocking {
//        try {
//            apiClient.subscribeNotifications(
//                listOf(TestValues.ETH_ADDRESS_1),
//                FirebaseToken(
//                    token = "fJeptHhFubg:APA91bFLeyoCHJ-uRBMcL-S6PgHTWVnps5vuQHVr6EOleWBcwdhep8TxosltldQXmbfFz4oVXvZWNimQE9IBncnqYLC7c1qcZNEZrF5X_aIltkLIx-bUEaaRvN2m2SfLWqLbgF_9vtom",
//                    typesParam = convertNotificationTypes(NotificationType.OUTGOING, NotificationType.INCOMING)
//                )
//            ).let { resp ->
//                Assert.assertTrue(resp.token.isNotEmpty())
//            }
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }
//
//    @Test
//    fun unsubscribe() = runBlocking {
//        try {
//            apiClient.unsubscribeNotifications(
//                listOf(TestValues.ETH_ADDRESS_1),
//                "fJeptHhFubg:APA91bFLeyoCHJ-uRBMcL-S6PgHTWVnps5vuQHVr6EOleWBcwdhep8TxosltldQXmbfFz4oVXvZWNimQE9IBncnqYLC7c1qcZNEZrF5X_aIltkLIx-bUEaaRvN2m2SfLWqLbgF_9vtom",
//                listOf(NotificationType.OUTGOING.toString(), NotificationType.INCOMING.toString())
//            ).let { resp ->
//                Assert.assertTrue(resp.token.isNotEmpty())
//            }
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }

// TODO: uncomment and change tokens run the tests below to test ETH token subscribe
//    @Test
//    fun subscribe() = runBlocking {
//        try {
//            apiClient.subscribeTokenNotifications(
//                listOf("0x13e879e897a0c4657a0096fd22542613259a14e8"),
//                EthTokenFirebaseToken(
//                    token = "d2IMYG9FRPyLnTrq2VS3JY:APA91bHe7fp6PEbZ16LFfDXk3F6Zo4V2M7rzL5gwiP7-xxTv2Ed35lRHzDFhCtzdAqqqaZooCa66dNa4YcC6kpd04dzDdhHThTaYupjcv1FStTRW0H3iosh3YEfOtH6gdNO3iA_XNsEj",
//                    typesParam = convertNotificationTypes(
//                        NotificationType.OUTGOING,
//                        NotificationType.INCOMING
//                    ),
//                    tokenAddress = "0x2fbdf70bbfc329a6b6bd6a83d83918127c9c0f45"
//                )
//            ).let { resp ->
//                Assert.assertTrue(resp.token.isNotEmpty())
//            }
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }
//
//    @Test
//    fun unsubscribe() = runBlocking {
//        try {
//            apiClient.unsubscribeTokenNotifications(
//                listOf("0x13e879e897a0c4657a0096fd22542613259a14e8"),
//                "d2IMYG9FRPyLnTrq2VS3JY:APA91bHe7fp6PEbZ16LFfDXk3F6Zo4V2M7rzL5gwiP7-xxTv2Ed35lRHzDFhCtzdAqqqaZooCa66dNa4YcC6kpd04dzDdhHThTaYupjcv1FStTRW0H3iosh3YEfOtH6gdNO3iA_XNsEj",
//                "0x2fbdf70bbfc329a6b6bd6a83d83918127c9c0f45",
//                listOf(NotificationType.OUTGOING.toString(), NotificationType.INCOMING.toString())
//            ).let { resp ->
//                Assert.assertTrue(resp.token.isNotEmpty())
//            }
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }
}