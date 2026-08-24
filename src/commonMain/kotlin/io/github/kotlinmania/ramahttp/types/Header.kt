// port-lint: source types/header.rs
package io.github.kotlinmania.ramahttp.types

/**
 * Represents an HTTP header name. Case-insensitive.
 */
public class HeaderName private constructor(
    private val normalized: String,
    private val original: String,
) {
    public constructor(name: String) : this(name.lowercase(), name)

    public fun asStr(): String = normalized

    override fun toString(): String = normalized

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeaderName) return false
        return normalized == other.normalized
    }

    override fun hashCode(): Int = normalized.hashCode()

    public companion object {
        public fun fromStatic(name: String): HeaderName {
            return HeaderName(name.lowercase(), name)
        }

        public fun fromString(name: String): HeaderName {
            return HeaderName(name.lowercase(), name)
        }

        public val ACCEPT: HeaderName = fromStatic("accept")
        public val ACCEPT_CHARSET: HeaderName = fromStatic("accept-charset")
        public val ACCEPT_ENCODING: HeaderName = fromStatic("accept-encoding")
        public val ACCEPT_LANGUAGE: HeaderName = fromStatic("accept-language")
        public val ACCEPT_RANGES: HeaderName = fromStatic("accept-ranges")
        public val ACCESS_CONTROL_ALLOW_CREDENTIALS: HeaderName = fromStatic("access-control-allow-credentials")
        public val ACCESS_CONTROL_ALLOW_HEADERS: HeaderName = fromStatic("access-control-allow-headers")
        public val ACCESS_CONTROL_ALLOW_METHODS: HeaderName = fromStatic("access-control-allow-methods")
        public val ACCESS_CONTROL_ALLOW_ORIGIN: HeaderName = fromStatic("access-control-allow-origin")
        public val ACCESS_CONTROL_EXPOSE_HEADERS: HeaderName = fromStatic("access-control-expose-headers")
        public val ACCESS_CONTROL_MAX_AGE: HeaderName = fromStatic("access-control-max-age")
        public val ACCESS_CONTROL_REQUEST_HEADERS: HeaderName = fromStatic("access-control-request-headers")
        public val ACCESS_CONTROL_REQUEST_METHOD: HeaderName = fromStatic("access-control-request-method")
        public val AGE: HeaderName = fromStatic("age")
        public val ALLOW: HeaderName = fromStatic("allow")
        public val ALT_SVC: HeaderName = fromStatic("alt-svc")
        public val AUTHORIZATION: HeaderName = fromStatic("authorization")
        public val CACHE_CONTROL: HeaderName = fromStatic("cache-control")
        public val CONNECTION: HeaderName = fromStatic("connection")
        public val CONTENT_DISPOSITION: HeaderName = fromStatic("content-disposition")
        public val CONTENT_ENCODING: HeaderName = fromStatic("content-encoding")
        public val CONTENT_LANGUAGE: HeaderName = fromStatic("content-language")
        public val CONTENT_LENGTH: HeaderName = fromStatic("content-length")
        public val CONTENT_LOCATION: HeaderName = fromStatic("content-location")
        public val CONTENT_RANGE: HeaderName = fromStatic("content-range")
        public val CONTENT_SECURITY_POLICY: HeaderName = fromStatic("content-security-policy")
        public val CONTENT_TYPE: HeaderName = fromStatic("content-type")
        public val COOKIE: HeaderName = fromStatic("cookie")
        public val DATE: HeaderName = fromStatic("date")
        public val ETAG: HeaderName = fromStatic("etag")
        public val EXPECT: HeaderName = fromStatic("expect")
        public val EXPIRES: HeaderName = fromStatic("expires")
        public val FORWARDED: HeaderName = fromStatic("forwarded")
        public val FROM: HeaderName = fromStatic("from")
        public val HOST: HeaderName = fromStatic("host")
        public val IF_MATCH: HeaderName = fromStatic("if-match")
        public val IF_MODIFIED_SINCE: HeaderName = fromStatic("if-modified-since")
        public val IF_NONE_MATCH: HeaderName = fromStatic("if-none-match")
        public val IF_RANGE: HeaderName = fromStatic("if-range")
        public val IF_UNMODIFIED_SINCE: HeaderName = fromStatic("if-unmodified-since")
        public val LAST_MODIFIED: HeaderName = fromStatic("last-modified")
        public val LINK: HeaderName = fromStatic("link")
        public val LOCATION: HeaderName = fromStatic("location")
        public val MAX_FORWARDS: HeaderName = fromStatic("max-forwards")
        public val ORIGIN: HeaderName = fromStatic("origin")
        public val PRAGMA: HeaderName = fromStatic("pragma")
        public val PROXY_AUTHENTICATE: HeaderName = fromStatic("proxy-authenticate")
        public val PROXY_AUTHORIZATION: HeaderName = fromStatic("proxy-authorization")
        public val RANGE: HeaderName = fromStatic("range")
        public val REFERER: HeaderName = fromStatic("referer")
        public val RETRY_AFTER: HeaderName = fromStatic("retry-after")
        public val SEC_WEBSOCKET_ACCEPT: HeaderName = fromStatic("sec-websocket-accept")
        public val SEC_WEBSOCKET_KEY: HeaderName = fromStatic("sec-websocket-key")
        public val SEC_WEBSOCKET_PROTOCOL: HeaderName = fromStatic("sec-websocket-protocol")
        public val SEC_WEBSOCKET_VERSION: HeaderName = fromStatic("sec-websocket-version")
        public val SERVER: HeaderName = fromStatic("server")
        public val SET_COOKIE: HeaderName = fromStatic("set-cookie")
        public val STRICT_TRANSPORT_SECURITY: HeaderName = fromStatic("strict-transport-security")
        public val TE: HeaderName = fromStatic("te")
        public val TRAILER: HeaderName = fromStatic("trailer")
        public val TRANSFER_ENCODING: HeaderName = fromStatic("transfer-encoding")
        public val UPGRADE: HeaderName = fromStatic("upgrade")
        public val USER_AGENT: HeaderName = fromStatic("user-agent")
        public val VARY: HeaderName = fromStatic("vary")
        public val VIA: HeaderName = fromStatic("via")
        public val WARNING: HeaderName = fromStatic("warning")
        public val WWW_AUTHENTICATE: HeaderName = fromStatic("www-authenticate")
        public val X_FORWARDED_FOR: HeaderName = fromStatic("x-forwarded-for")
        public val X_FORWARDED_HOST: HeaderName = fromStatic("x-forwarded-host")
        public val X_FORWARDED_PROTO: HeaderName = fromStatic("x-forwarded-proto")
        public val X_REQUEST_ID: HeaderName = fromStatic("x-request-id")
    }
}

/**
 * Represents an HTTP header value.
 */
public class HeaderValue(
    private val bytes: ByteArray,
) {
    public constructor(str: String) : this(str.encodeToByteArray())
    public fun asBytes(): ByteArray = bytes.copyOf()

    public fun toStr(): String = bytes.decodeToString()

    public fun toStrOrNull(): String? {
        return try {
            bytes.decodeToString()
        } catch (_: Throwable) {
            null
        }
    }

    override fun toString(): String = toStr()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeaderValue) return false
        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int = bytes.contentHashCode()

    public companion object {
        public fun fromStatic(str: String): HeaderValue = HeaderValue(str.encodeToByteArray())
        public fun fromString(str: String): HeaderValue = HeaderValue(str.encodeToByteArray())
        public fun fromBytes(bytes: ByteArray): HeaderValue = HeaderValue(bytes.copyOf())
        public fun fromName(name: HeaderName): HeaderValue = fromString(name.asStr())
    }
}

/**
 * A multi-map of HTTP headers.
 */
public class HeaderMap : Iterable<Pair<HeaderName, HeaderValue>> {
    private val map: MutableMap<HeaderName, MutableList<HeaderValue>> = mutableMapOf()

    public constructor()

    public constructor(other: HeaderMap) {
        for ((k, vList) in other.map) {
            map[k] = vList.toMutableList()
        }
    }

    public fun get(name: HeaderName): HeaderValue? {
        return map[name]?.firstOrNull()
    }

    public fun get(name: String): HeaderValue? {
        return get(HeaderName.fromString(name))
    }

    public fun getAll(name: HeaderName): List<HeaderValue> {
        return map[name]?.toList() ?: emptyList()
    }

    public fun getAll(name: String): List<HeaderValue> {
        return getAll(HeaderName.fromString(name))
    }

    public fun insert(name: HeaderName, value: HeaderValue): HeaderValue? {
        val prev = map[name]?.firstOrNull()
        map[name] = mutableListOf(value)
        return prev
    }

    public fun insert(name: String, value: String): HeaderValue? {
        return insert(HeaderName.fromString(name), HeaderValue.fromString(value))
    }

    public fun append(name: HeaderName, value: HeaderValue) {
        map.getOrPut(name) { mutableListOf() }.add(value)
    }

    public fun append(name: String, value: String) {
        append(HeaderName.fromString(name), HeaderValue.fromString(value))
    }

    public fun remove(name: HeaderName): HeaderValue? {
        val list = map.remove(name)
        return list?.firstOrNull()
    }

    public fun remove(name: String): HeaderValue? {
        return remove(HeaderName.fromString(name))
    }

    public fun containsKey(name: HeaderName): Boolean {
        return map.containsKey(name)
    }

    public fun containsKey(name: String): Boolean {
        return containsKey(HeaderName.fromString(name))
    }

    public fun contains(name: HeaderName): Boolean = containsKey(name)

    public fun keys(): Set<HeaderName> = map.keys.toSet()

    public fun entries(): List<Pair<HeaderName, HeaderValue>> {
        val result = mutableListOf<Pair<HeaderName, HeaderValue>>()
        for ((name, values) in map) {
            for (value in values) {
                result.add(Pair(name, value))
            }
        }
        return result
    }

    public fun clear() {
        map.clear()
    }

    public fun len(): Int = map.values.sumOf { it.size }

    public fun isEmpty(): Boolean = map.isEmpty()

    override fun iterator(): Iterator<Pair<HeaderName, HeaderValue>> {
        return entries().iterator()
    }

    public fun clone(): HeaderMap = HeaderMap(this)
}
