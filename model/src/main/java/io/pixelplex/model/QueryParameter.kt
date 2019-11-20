package io.pixelplex.model

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