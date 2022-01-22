fun main() {
    println(
        """
        SET <key> <value>   // store the value for key
        GET <key>           // return the current value for key
        DELETE <key>        // remove the entry for key
        COUNT <value>       // return the number of keys that have the given value
        BEGIN               // start a new transaction
        COMMIT              // complete the current transaction
        ROLLBACK            // revert to state prior to BEGIN call
    """.trimIndent()
    )

    while (true) {
        print("> ")
        val input = readLine() ?: break

        val args = input.lowercase().split(" ")
        if (args.isEmpty()) {
            return
        }

        when (args[0]) {
            "set" -> set(args)
            "get" -> get(args)
            "delete" -> delete(args)
            "count" -> count(args)
            "begin" -> begin(args)
            "commit" -> commit(args)
            "rollback" -> rollback(args)
            else -> println("Unknown command $input")
        }
    }

    println("Hodl till it gets to the moon")
}

private var transaction = Transaction()

private fun set(args: List<String>) {
    if (args.size != 3) {
        println("SET must have exactly 2 params")
        return
    }

    transaction.set(args[1], args[2])
}

private fun get(args: List<String>) {
    if (args.size != 2) {
        println("GET must have exactly 1 param")
        return
    }

    println(transaction.get(args[1]) ?: "key not set")
}

private fun delete(args: List<String>) {
    if (args.size != 2) {
        println("DELETE must have exactly 1 param")
        return
    }

    transaction.delete(args[1])
}

private fun count(args: List<String>) {
    if (args.size != 2) {
        println("COUNT must have exactly 1 param")
        return
    }

    println(transaction.count(args[1]))
}

private fun begin(args: List<String>) {
    if (args.size != 1) {
        println("BEGIN must have no params")
        return
    }

    transaction = transaction.begin()
}

private fun commit(args: List<String>) {
    if (args.size != 1) {
        println("COMMIT must have no params")
        return
    }

    val commit = transaction.commit()
    if (commit == null) {
        println("no transaction")
    } else {
        transaction = commit
    }
}

private fun rollback(args: List<String>) {
    if (args.size != 1) {
        println("ROLLBACK must have no params")
        return
    }

    val rollback = transaction.rollback()
    if (rollback == null) {
        println("no transaction")
    } else {
        transaction = rollback
    }
}
