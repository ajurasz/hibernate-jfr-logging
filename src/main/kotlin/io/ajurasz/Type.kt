package io.ajurasz

import java.sql.Timestamp
import java.time.format.DateTimeFormatter

sealed class Type {

    abstract override fun toString(): String

    class AnyType(private val value: Any) : Type() {
        override fun toString() = value.toString()
    }

    class NumberType(private val value: Number) : Type() {
        override fun toString() = value.toString()
    }

    class StringType(private val value: String) : Type() {
        override fun toString() = "'$value'"
    }

    class TimestampType(private val value: Timestamp) : Type() {
        override fun toString() =
            "'${value.toLocalDateTime().format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS"))}'"
    }
}

fun typeProvider(value: Any) = when (value) {
    is String -> Type.StringType(value)
    is Number -> Type.NumberType(value)
    is Timestamp -> Type.TimestampType(value)
    else -> Type.AnyType(value)
}
