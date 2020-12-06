package io.ajurasz

import net.ttddyy.dsproxy.proxy.ParameterSetOperation

class Query private constructor(private val sql: String, private val params: Collection<Param>) {
    companion object {
        fun from(sql: String, params: Collection<ParameterSetOperation>) = Query(sql, toParams(params))

        private fun toParams(params: Collection<ParameterSetOperation>) =
            params.map { Param(it.args[0] as Int, it.args[1]) }
                .sortedBy { it.index }
    }

    fun toSql(): String {
        val queryParams = ArrayDeque(params)
        return sql.asSequence()
            .map { resolvePlaceholders(it, queryParams) }
            .joinToString(separator = "")
    }

    private fun resolvePlaceholders(queryCharacter: Char, queryParams: ArrayDeque<Param>) =
        if (isParameterPlaceholder(queryCharacter)) resolveParameter(queryParams).toString() else queryCharacter

    private val isParameterPlaceholder = { char: Char -> '?' == char }

    private fun resolveParameter(queryParams: ArrayDeque<Param>) = typeProvider(queryParams.removeFirst().value)
}

private class Param(val index: Int, val value: Any)
