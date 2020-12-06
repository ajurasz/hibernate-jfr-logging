package io.ajurasz

import jdk.jfr.*
import net.ttddyy.dsproxy.ExecutionInfo
import net.ttddyy.dsproxy.QueryInfo
import net.ttddyy.dsproxy.listener.QueryExecutionListener

class QueryRecorder : QueryExecutionListener by QueryExecutionListener.DEFAULT {
    override fun beforeQuery(execInfo: ExecutionInfo?, queryInfoList: MutableList<QueryInfo>) {
        resolveQuery(queryInfoList)
                .forEach(::publishEvent)
    }

    private fun resolveQuery(queryInfoList: MutableList<QueryInfo>) = queryInfoList
            .flatMap { it.parametersList.map { params -> Pair(it.query, params) } }
            .map { (query, params) -> Query.from(query, params) }

    private fun publishEvent(query: Query) = QueryEvent(query.toSql()).commit()
}

@Name("io.ajurasz.QueryEvent")
@Label("Query")
@Category("Hibernate")
@StackTrace(false)
class QueryEvent(@Label("Sql") val sql: String): Event()
