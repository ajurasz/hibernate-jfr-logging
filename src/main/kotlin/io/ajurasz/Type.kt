package io.ajurasz

import java.sql.Timestamp
import java.time.format.DateTimeFormatter

sealed class Type {

    abstract override fun toString(): String

    companion object {
        fun of(value: Any) = when (value) {
            is String -> StringType(value)
            is Number -> NumberType(value)
            is Timestamp -> TimestampType(value)
            else -> AnyType(value)
        }
    }

    private class AnyType(private val value: Any) : Type() {
        override fun toString() = value.toString()
    }

    private class NumberType(private val value: Number) : Type() {
        override fun toString() = value.toString()
    }

    private class StringType(private val value: String) : Type() {
        override fun toString() = "'$value'"
    }

    private class TimestampType(private val value: Timestamp) : Type() {
        override fun toString() =
                "'${value.toLocalDateTime().format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS"))}'"
    }
}
