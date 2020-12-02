package io.ajurasz

import net.ttddyy.dsproxy.ExecutionInfo
import net.ttddyy.dsproxy.QueryInfo
import net.ttddyy.dsproxy.listener.QueryExecutionListener

class QueryRecorder : QueryExecutionListener by QueryExecutionListener.DEFAULT {
    override fun beforeQuery(execInfo: ExecutionInfo?, queryInfoList: MutableList<QueryInfo>) {
        resolveQuery(queryInfoList)
    }

    private fun resolveQuery(queryInfoList: MutableList<QueryInfo>) {
        queryInfoList
            .flatMap { it.parametersList.map { params -> Pair(it.query, params) } }
            .forEach {
                val query = Query.from(it.first, it.second)
                println(query.toSql())
            }
    }
}
