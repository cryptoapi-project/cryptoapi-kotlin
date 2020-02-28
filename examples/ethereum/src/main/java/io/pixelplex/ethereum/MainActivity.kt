package io.pixelplex.ethereum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.pixelplex.mobile.CryptoApiFramework
import io.pixelplex.mobile.core.CryptoApi

class MainActivity : AppCompatActivity() {

    private val apiClient by lazy {
        CryptoApiFramework.getInstance(
            CALL_TIMEOUT,
            CONNECT_TIMEOUT,
            READ_TIMEOUT,
            CRYPTO_API_KEY,
            CryptoApi.URL.TESTNET
        ).ethereumAsyncApi
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        
    }

    companion object {
        private const val CALL_TIMEOUT = 30000L
        private const val READ_TIMEOUT = 30000L
        private const val CONNECT_TIMEOUT = 15000L

        private const val CRYPTO_API_KEY = "5de552d7efc6ff2e1b09d946cc5263e346003a93ab28bf2ffeb24979da85a1f5"

        private const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
        private const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"
        private const val HASH = "0x1ed7742bd59a7f94dac9eaac8ae4db683d365e6ef8a1609abe167adb6cbd20ed"
        private const val CONTRACT_ADDRESS = "0x30820824cadaf67d19989263bcabe03fc85d0321"
        private const val TX =
            "0xf86e8386ca038602bba7f5220083632ea0941de29f644d555fe9cc3241e1083de0868f959bfa8545d964b800801ca04ef1f13c58af9a9ac4be66b838a238b24db798d585d882865637fdc35bdc49c4a04b7d1dfc3d9672080347a0d3559628f5f757bd6f6a005d1c4f7cdccce020ea02"

    }
}
