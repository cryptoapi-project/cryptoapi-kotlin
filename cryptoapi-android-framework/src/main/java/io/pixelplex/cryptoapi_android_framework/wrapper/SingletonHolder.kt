package io.pixelplex.cryptoapi_android_framework.wrapper

open class SingletonHolder<out T: Any, in CAT, in CNT, in TKN>(creator: (CAT, CNT, TKN) -> T) {
    private var creator: ((CAT, CNT, TKN) -> T)? = creator

    @Volatile private var instance: T? = null

    fun getInstance(callTimeout: CAT, connectTimeout: CNT, token: TKN): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

         synchronized(this) {
            val checkInstanceAgain = instance
            return if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(callTimeout, connectTimeout, token)
                instance = created
                creator = null
                created
            }
        }
    }
}