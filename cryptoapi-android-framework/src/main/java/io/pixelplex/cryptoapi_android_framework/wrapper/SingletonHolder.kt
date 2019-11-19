package io.pixelplex.cryptoapi_android_framework.wrapper

open class SingletonHolder<out T: Any, in CAT, in CNT>(creator: (CAT, CNT) -> T) {
    private var creator: ((CAT, CNT) -> T)? = creator

    @Volatile private var instance: T? = null

    fun getInstance(callTimeout: CAT, connectTimeout: CNT): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

         synchronized(this) {
            val checkInstanceAgain = instance
            return if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(callTimeout, connectTimeout)
                instance = created
                creator = null
                created
            }
        }
    }
}