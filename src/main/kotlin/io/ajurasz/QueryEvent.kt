package io.ajurasz

import jdk.jfr.Category
import jdk.jfr.Event
import jdk.jfr.Label
import jdk.jfr.Name
import jdk.jfr.StackTrace

@Name("io.ajurasz.QueryEvent")
@Label("Query")
@Category("DataSource")
@StackTrace(false)
class QueryEvent(@Label("Sql") val sql: String) : Event()
