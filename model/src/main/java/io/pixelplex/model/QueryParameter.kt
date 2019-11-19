package io.pixelplex.model

class QueryParameter(
    val name: String,
    val value: Any,
    val type: QueryType
)

enum class QueryType {
    QUERY,
    PATH,
    BODY
}