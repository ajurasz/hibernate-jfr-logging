package io.ajurasz

import java.lang.Thread.sleep
import java.util.*

fun main() {
    while (true) {
        DB.inTransaction { session ->
            (1..5).forEach {
                session.save(Post("Post #$it", "Some content ${UUID.randomUUID()}"))
            }
        }

        sleep(30_000)
    }
}
