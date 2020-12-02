package io.ajurasz

import com.mysql.cj.jdbc.MysqlDataSource
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Environment

object DB {
    private val sessionFactory: SessionFactory

    init {
        val registry = StandardServiceRegistryBuilder()
                .configure()
                .applySettings(runtimeSettings())
                .build()

        sessionFactory = MetadataSources(registry)
                .addAnnotatedClass(Post::class.java)
                .buildMetadata()
                .buildSessionFactory()
    }

    private fun runtimeSettings(): MutableMap<String, Any> {
        return mutableMapOf(
                Environment.DATASOURCE to dataSourceProxy()
        )
    }

    private fun dataSourceProxy() = ProxyDataSourceBuilder.create(dataSource())
            .listener(QueryRecorder())
            .build()

    private fun dataSource() = MysqlDataSource().also {
        it.setURL(dbUrl())
        it.user = dbUser()
        it.password = dbPassword()
    }

    private fun dbUrl() =
            "jdbc:mysql://${System.getenv("DB_HOSTNAME")}:${System.getenv("DB_PORT").toInt()}/${System.getenv("DB_NAME")}"

    private fun dbUser() = System.getenv("DB_USERNAME")

    private fun dbPassword() = System.getenv("DB_PASSWORD")

    fun inTransaction(fn: (Session) -> Unit) {
        val session = sessionFactory.openSession()
        session.use {
            val txn = it.beginTransaction()
            try {
                fn.invoke(it)
                txn.commit()
            } catch (e: Exception) {
                txn.rollback()
                e.printStackTrace()
            }
        }
    }
}
