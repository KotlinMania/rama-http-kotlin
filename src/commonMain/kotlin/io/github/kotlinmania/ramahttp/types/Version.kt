// port-lint: source rama-http/src/types/version.rs
package io.github.kotlinmania.ramahttp.types

/**
 * Represents a version of the HTTP protocol.
 */
public enum class Version {
    HTTP_09,
    HTTP_10,
    HTTP_11,
    HTTP_2,
    HTTP_3,
    ;

    public fun asStr(): String =
        when (this) {
            HTTP_09 -> "HTTP/0.9"
            HTTP_10 -> "HTTP/1.0"
            HTTP_11 -> "HTTP/1.1"
            HTTP_2 -> "HTTP/2.0"
            HTTP_3 -> "HTTP/3.0"
        }

    public companion object {
        public fun fromString(str: String): Version? =
            when (str.trim().uppercase()) {
                "HTTP/0.9", "0.9" -> HTTP_09
                "HTTP/1.0", "1.0" -> HTTP_10
                "HTTP/1.1", "1.1" -> HTTP_11
                "HTTP/2.0", "HTTP/2", "2.0", "2", "H2" -> HTTP_2
                "HTTP/3.0", "HTTP/3", "3.0", "3", "H3" -> HTTP_3
                else -> null
            }
    }
}
