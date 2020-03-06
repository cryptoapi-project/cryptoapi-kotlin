package io.pixelplex.bitcoinjexample.ethereum_components

/**
 * Describes value [value] and it's type [type] of contract method parameter
 *
 * @author Dmitriy Bushuev
 */
data class InputValue(val type: InputValueType, val value: String)
