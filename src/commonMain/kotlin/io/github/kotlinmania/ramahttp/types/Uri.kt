// port-lint: source rama-http/src/lib.rs
package io.github.kotlinmania.ramahttp.types

public class Scheme(
    public val asStr: String,
) {
    override fun toString(): String = asStr

    override fun equals(other: Any?): Boolean = other is Scheme && asStr.equals(other.asStr, ignoreCase = true)

    override fun hashCode(): Int = asStr.lowercase().hashCode()

    public companion object {
        public val HTTP: Scheme = Scheme("http")
        public val HTTPS: Scheme = Scheme("https")
    }
}

public class Authority(
    public val asStr: String,
) {
    public val host: String
    public val portU16: Int?

    init {
        val colonIdx = asStr.lastIndexOf(':')
        if (colonIdx != -1 && !asStr.endsWith("]")) {
            val hostPart = asStr.substring(0, colonIdx)
            val portPart = asStr.substring(colonIdx + 1)
            val parsedPort = portPart.toIntOrNull()
            if (parsedPort != null) {
                host = hostPart
                portU16 = parsedPort
            } else {
                host = asStr
                portU16 = null
            }
        } else {
            host = asStr
            portU16 = null
        }
    }

    override fun toString(): String = asStr

    override fun equals(other: Any?): Boolean = other is Authority && asStr == other.asStr

    override fun hashCode(): Int = asStr.hashCode()

    public companion object {
        public fun fromString(str: String): Authority = Authority(str)
    }
}

public class PathAndQuery(
    public val asStr: String,
) {
    public val path: String
    public val query: String?

    init {
        val qIdx = asStr.indexOf('?')
        if (qIdx != -1) {
            path = asStr.substring(0, qIdx)
            query = asStr.substring(qIdx + 1)
        } else {
            path = asStr
            query = null
        }
    }

    override fun toString(): String = asStr

    override fun equals(other: Any?): Boolean = other is PathAndQuery && asStr == other.asStr

    override fun hashCode(): Int = asStr.hashCode()

    public companion object {
        public fun fromString(str: String): PathAndQuery = PathAndQuery(str)
    }
}

public class UriParts(
    public var scheme: Scheme? = null,
    public var authority: Authority? = null,
    public var pathAndQuery: PathAndQuery? = null,
)

public class Uri(
    public val scheme: Scheme? = null,
    public val authority: Authority? = null,
    public val pathAndQuery: PathAndQuery? = null,
) {
    public val path: String get() = pathAndQuery?.path ?: "/"
    public val query: String? get() = pathAndQuery?.query
    public val host: String? get() = authority?.host

    public fun intoParts(): UriParts = UriParts(scheme, authority, pathAndQuery)

    override fun toString(): String {
        val sb = StringBuilder()
        if (scheme != null) {
            sb.append(scheme.asStr).append("://")
        }
        if (authority != null) {
            sb.append(authority.asStr)
        }
        if (pathAndQuery != null) {
            val paq = pathAndQuery.asStr
            if (authority != null && !paq.startsWith("/")) {
                sb.append("/")
            }
            sb.append(paq)
        } else if (scheme != null || authority != null) {
            sb.append("/")
        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Uri) return false
        return toString() == other.toString()
    }

    override fun hashCode(): Int = toString().hashCode()

    public companion object {
        public fun fromParts(parts: UriParts): Uri = Uri(parts.scheme, parts.authority, parts.pathAndQuery)

        public fun fromString(str: String): Uri {
            var input = str.trim()
            var scheme: Scheme? = null
            var authority: Authority? = null
            var pathAndQuery: PathAndQuery? = null

            val schemeIdx = input.indexOf("://")
            if (schemeIdx != -1) {
                scheme = Scheme(input.substring(0, schemeIdx))
                input = input.substring(schemeIdx + 3)
                val slashIdx = input.indexOf('/')
                val qIdx = input.indexOf('?')
                val endAuth =
                    when {
                        slashIdx != -1 && qIdx != -1 -> minOf(slashIdx, qIdx)
                        slashIdx != -1 -> slashIdx
                        qIdx != -1 -> qIdx
                        else -> -1
                    }
                if (endAuth != -1) {
                    authority = Authority(input.substring(0, endAuth))
                    input = input.substring(endAuth)
                } else {
                    authority = Authority(input)
                    input = "/"
                }
            } else if (input.startsWith("//")) {
                input = input.substring(2)
                val slashIdx = input.indexOf('/')
                if (slashIdx != -1) {
                    authority = Authority(input.substring(0, slashIdx))
                    input = input.substring(slashIdx)
                } else {
                    authority = Authority(input)
                    input = "/"
                }
            }

            if (input.isNotEmpty()) {
                pathAndQuery = PathAndQuery(input)
            } else {
                pathAndQuery = PathAndQuery("/")
            }

            return Uri(scheme, authority, pathAndQuery)
        }
    }
}
