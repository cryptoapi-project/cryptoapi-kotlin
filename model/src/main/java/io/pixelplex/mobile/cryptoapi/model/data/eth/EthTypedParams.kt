package io.pixelplex.mobile.cryptoapi.model.data.eth

/**
 * Combines some specific properties of list which is contained in some another call bodies.
 * It could be used which we have a list of strings has to be shown as a string is separated via
 * a separator
 *
 * @author Sergey Krupenich
 */
class EthTypedParams(vararg params: String) {
    private val paramsStrings: List<String>

    init {
        when(params.count().compareTo(0) == 0) {
            true ->
                throw IllegalArgumentException("At least 1 address must be declared")
            else ->
                paramsStrings = params.toList()
        }
    }

    fun string() =
        paramsStrings.joinToString(separator = SEPARATOR)

    fun getList() = paramsStrings

    private companion object {
        const val SEPARATOR = ","
    }
}