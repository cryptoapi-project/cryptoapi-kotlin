package io.pixelplex.bitcoinjexample.ethereum_components

import java.security.NoSuchAlgorithmException
import java.security.Provider
import java.security.Signature

object ECSignatureFactory {

    private const val RAW_ALGORITHM = "NONEwithECDSA"

    /**
     * Returns [Signature] by [provider]
     */
    fun getRawInstance(provider: Provider): Signature {
        try {
            return Signature.getInstance(RAW_ALGORITHM, provider)
        } catch (ex: NoSuchAlgorithmException) {
            throw AssertionError("Assumed the JRE supports NONEwithECDSA signatures", ex)
        }
    }
}
