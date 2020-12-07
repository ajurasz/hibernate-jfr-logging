package io.ajurasz

import net.ttddyy.dsproxy.ExecutionInfo
import net.ttddyy.dsproxy.QueryInfo
import net.ttddyy.dsproxy.listener.QueryExecutionListener

class QueryRecorder : QueryExecutionListener by QueryExecutionListener.DEFAULT {
    override fun afterQuery(execInfo: ExecutionInfo?, queryInfoList: MutableList<QueryInfo>) {
        resolveQuery(queryInfoList)
                .forEach(::publishEvent)
    }

    private fun resolveQuery(queryInfoList: MutableList<QueryInfo>) = queryInfoList
            .flatMap { it.parametersList.map { params -> Pair(it.query, params) } }
            .map { (query, params) -> Query.from(query, params) }

    private fun publishEvent(query: Query) = QueryEvent(query.toSql()).commit()
}
