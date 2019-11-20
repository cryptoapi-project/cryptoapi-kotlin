package io.pixelplex.model.data

class EthAddresses(vararg addresses: String) {
    private val addressesStrings: List<String>

    init {
        when(addresses.count().compareTo(0) == 0) {
            true ->
                throw IllegalArgumentException("At least 1 address must be declared")
            else ->
                addressesStrings = addresses.toList()
        }
    }

    fun string() =
        addressesStrings.joinToString(separator = SEPARATOR)

    private companion object {
        const val SEPARATOR = ","
    }
}