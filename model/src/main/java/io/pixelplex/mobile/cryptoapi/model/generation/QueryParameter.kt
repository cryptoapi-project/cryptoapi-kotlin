package io.pixelplex.mobile.cryptoapi.model.generation

class QueryParameter<T>(
    val name: String,
    val value: RequestParameter<T>,
    val type: QueryType
)

enum class QueryType {
    QUERY,
    PATH,
    BODY
}