class Transaction(
    storage: HashMap<String, String> = HashMap(),
    occurrences: HashMap<String, Int> = HashMap(),
    private val parent: Transaction? = null
) {
    private var storage: HashMap<String, String>
    private var occurrences: HashMap<String, Int>

    private var child: Transaction? = null

    init {
        this.storage = HashMap(storage)
        this.occurrences = HashMap(occurrences)
    }

    fun set(key: String, value: String) {
        val oldValue = storage.put(key, value)
        occurrences[value] = (occurrences[value] ?: 0) + 1

        if (oldValue != null) {
            val oldValueOccurrences = occurrences[oldValue] ?: 0
            if (oldValueOccurrences == 1) {
                occurrences.remove(oldValue)
            } else {
                occurrences[oldValue] = oldValueOccurrences - 1
            }
        }
    }

    fun get(key: String): String? =
        storage[key]

    fun delete(key: String) {
        val result = storage.remove(key)
        if (result != null) {
            occurrences[result] = (occurrences[result] ?: 0) - 1
        }
    }

    fun count(value: String): Int =
        occurrences[value] ?: 0

    fun begin(): Transaction =
        Transaction(storage = storage, occurrences = occurrences, parent = this)
            .also { this.child = it }

    fun commit(): Transaction? =
        parent?.also {
            it.storage = storage
            it.occurrences = occurrences
            it.child = null
        }

    fun rollback(): Transaction? =
        parent?.also {
            it.child = null
        }
}