package io.pixelplex.model.data

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

    private companion object {
        const val SEPARATOR = ","
    }
}