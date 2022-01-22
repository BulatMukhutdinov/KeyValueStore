# Description
The assignment is to build an interactive command line interface to a transactional key value store.
A user should be able to compile and run this program and get an interactive shell with a prompt where they can type commands.
The user can enter commands to set/get/delete key/value pairs and count values. All values can be treated as strings, no need to differentiate by type.
The key/value data only needs to exist in memory for the session, it does not need to be written to disk.

The interface should also allow the user to perform operations in transactions, which allows the user to commit or roll back their changes to the key value store.
That includes the ability to nest transactions and roll back and commit within nested transactions. The solution shouldn't depend on any third party libraries.
The interface should support the following commands:
```
SET <key> <value>   // store the value for key
GET <key>           // return the current value for key
DELETE <key>        // remove the entry for key
COUNT <value>       // return the number of keys that have the given value
BEGIN               // start a new transaction
COMMIT              // complete the current transaction
ROLLBACK            // revert to state prior to BEGIN call
```

# Assumptions
1) Storage size is limited to hashmap size
2) Multithreading is not supported. I am not sure anyway how it is possible to multithread this console app but I am definitely sure that there are smart asses that would find a way
3) No prior knowledge about how frequent and how many operations are performed so load factor and initial capacity are left on default values
4) Performance prevails memory

# Possible enhancements
1) If the size of the storage constantly growing along with the nesting level, instead of copying the maps to the child Transaction we can gather diffs and later on apply these diffs to parent's maps
