// port-lint: source types/method.rs
package io.github.kotlinmania.ramahttp.types

/**
 * The Request Method (VERB)
 *
 * This type also provides constants for common methods: [GET], [POST], etc.
 */
public class Method private constructor(
    private val inner: String,
) {
    public fun asStr(): String = inner

    override fun toString(): String = inner

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Method) return false
        return inner.equals(other.inner, ignoreCase = false)
    }

    override fun hashCode(): Int = inner.hashCode()

    public companion object {
        public val OPTIONS: Method = Method("OPTIONS")
        public val GET: Method = Method("GET")
        public val POST: Method = Method("POST")
        public val PUT: Method = Method("PUT")
        public val DELETE: Method = Method("DELETE")
        public val HEAD: Method = Method("HEAD")
        public val TRACE: Method = Method("TRACE")
        public val CONNECT: Method = Method("CONNECT")
        public val PATCH: Method = Method("PATCH")

        public fun fromBytes(bytes: ByteArray): Method {
            val str = bytes.decodeToString()
            return fromString(str)
        }

        public fun fromString(str: String): Method {
            return when (str.uppercase()) {
                "OPTIONS" -> OPTIONS
                "GET" -> GET
                "POST" -> POST
                "PUT" -> PUT
                "DELETE" -> DELETE
                "HEAD" -> HEAD
                "TRACE" -> TRACE
                "CONNECT" -> CONNECT
                "PATCH" -> PATCH
                else -> Method(str)
            }
        }
    }
}
