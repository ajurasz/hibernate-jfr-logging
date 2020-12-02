package io.ajurasz

fun main() {
    DB.inTransaction { session ->
        session.save(Post("Post no.1", "Some content"))
    }
}
