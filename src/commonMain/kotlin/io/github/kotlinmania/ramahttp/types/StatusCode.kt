// port-lint: source rama-http/src/lib.rs
package io.github.kotlinmania.ramahttp.types

/**
 * An HTTP status code (100-599).
 */
public class StatusCode(
    public val code: UShort,
) {
    public constructor(code: Int) : this(code.toUShort())

    public fun asU16(): UShort = code

    public fun asInt(): Int = code.toInt()

    public fun isInformational(): Boolean = code in 100u..199u

    public fun isSuccess(): Boolean = code in 200u..299u

    public fun isRedirection(): Boolean = code in 300u..399u

    public fun isClientError(): Boolean = code in 400u..499u

    public fun isServerError(): Boolean = code in 500u..599u

    public fun canonicalReason(): String? =
        when (code.toInt()) {
            100 -> "Continue"
            101 -> "Switching Protocols"
            102 -> "Processing"
            103 -> "Early Hints"
            200 -> "OK"
            201 -> "Created"
            202 -> "Accepted"
            203 -> "Non-Authoritative Information"
            204 -> "No Content"
            205 -> "Reset Content"
            206 -> "Partial Content"
            207 -> "Multi-Status"
            208 -> "Already Reported"
            226 -> "IM Used"
            300 -> "Multiple Choices"
            301 -> "Moved Permanently"
            302 -> "Found"
            303 -> "See Other"
            304 -> "Not Modified"
            305 -> "Use Proxy"
            307 -> "Temporary Redirect"
            308 -> "Permanent Redirect"
            400 -> "Bad Request"
            401 -> "Unauthorized"
            402 -> "Payment Required"
            403 -> "Forbidden"
            404 -> "Not Found"
            405 -> "Method Not Allowed"
            406 -> "Not Acceptable"
            407 -> "Proxy Authentication Required"
            408 -> "Request Timeout"
            409 -> "Conflict"
            410 -> "Gone"
            411 -> "Length Required"
            412 -> "Precondition Failed"
            413 -> "Payload Too Large"
            414 -> "URI Too Long"
            415 -> "Unsupported Media Type"
            416 -> "Range Not Satisfiable"
            417 -> "Expectation Failed"
            418 -> "I'm a teapot"
            421 -> "Misdirected Request"
            422 -> "Unprocessable Entity"
            423 -> "Locked"
            424 -> "Failed Dependency"
            425 -> "Too Early"
            426 -> "Upgrade Required"
            428 -> "Precondition Required"
            429 -> "Too Many Requests"
            431 -> "Request Header Fields Too Large"
            451 -> "Unavailable For Legal Reasons"
            500 -> "Internal Server Error"
            501 -> "Not Implemented"
            502 -> "Bad Gateway"
            503 -> "Service Unavailable"
            504 -> "Gateway Timeout"
            505 -> "HTTP Version Not Supported"
            506 -> "Variant Also Negotiates"
            507 -> "Insufficient Storage"
            508 -> "Loop Detected"
            510 -> "Not Extended"
            511 -> "Network Authentication Required"
            else -> null
        }

    override fun toString(): String = code.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StatusCode) return false
        return code == other.code
    }

    override fun hashCode(): Int = code.hashCode()

    public companion object {
        public val CONTINUE: StatusCode = StatusCode(100)
        public val SWITCHING_PROTOCOLS: StatusCode = StatusCode(101)
        public val PROCESSING: StatusCode = StatusCode(102)
        public val OK: StatusCode = StatusCode(200)
        public val CREATED: StatusCode = StatusCode(201)
        public val ACCEPTED: StatusCode = StatusCode(202)
        public val NO_CONTENT: StatusCode = StatusCode(204)
        public val RESET_CONTENT: StatusCode = StatusCode(205)
        public val PARTIAL_CONTENT: StatusCode = StatusCode(206)
        public val MULTIPLE_CHOICES: StatusCode = StatusCode(300)
        public val MOVED_PERMANENTLY: StatusCode = StatusCode(301)
        public val FOUND: StatusCode = StatusCode(302)
        public val SEE_OTHER: StatusCode = StatusCode(303)
        public val NOT_MODIFIED: StatusCode = StatusCode(304)
        public val USE_PROXY: StatusCode = StatusCode(305)
        public val TEMPORARY_REDIRECT: StatusCode = StatusCode(307)
        public val PERMANENT_REDIRECT: StatusCode = StatusCode(308)
        public val BAD_REQUEST: StatusCode = StatusCode(400)
        public val UNAUTHORIZED: StatusCode = StatusCode(401)
        public val PAYMENT_REQUIRED: StatusCode = StatusCode(402)
        public val FORBIDDEN: StatusCode = StatusCode(403)
        public val NOT_FOUND: StatusCode = StatusCode(404)
        public val METHOD_NOT_ALLOWED: StatusCode = StatusCode(405)
        public val NOT_ACCEPTABLE: StatusCode = StatusCode(406)
        public val PROXY_AUTHENTICATION_REQUIRED: StatusCode = StatusCode(407)
        public val REQUEST_TIMEOUT: StatusCode = StatusCode(408)
        public val CONFLICT: StatusCode = StatusCode(409)
        public val GONE: StatusCode = StatusCode(410)
        public val LENGTH_REQUIRED: StatusCode = StatusCode(411)
        public val PRECONDITION_FAILED: StatusCode = StatusCode(412)
        public val PAYLOAD_TOO_LARGE: StatusCode = StatusCode(413)
        public val URI_TOO_LONG: StatusCode = StatusCode(414)
        public val UNSUPPORTED_MEDIA_TYPE: StatusCode = StatusCode(415)
        public val RANGE_NOT_SATISFIABLE: StatusCode = StatusCode(416)
        public val EXPECTATION_FAILED: StatusCode = StatusCode(417)
        public val IM_A_TEAPOT: StatusCode = StatusCode(418)
        public val MISDIRECTED_REQUEST: StatusCode = StatusCode(421)
        public val UNPROCESSABLE_ENTITY: StatusCode = StatusCode(422)
        public val LOCKED: StatusCode = StatusCode(423)
        public val FAILED_DEPENDENCY: StatusCode = StatusCode(424)
        public val TOO_EARLY: StatusCode = StatusCode(425)
        public val UPGRADE_REQUIRED: StatusCode = StatusCode(426)
        public val PRECONDITION_REQUIRED: StatusCode = StatusCode(428)
        public val TOO_MANY_REQUESTS: StatusCode = StatusCode(429)
        public val REQUEST_HEADER_FIELDS_TOO_LARGE: StatusCode = StatusCode(431)
        public val UNAVAILABLE_FOR_LEGAL_REASONS: StatusCode = StatusCode(451)
        public val INTERNAL_SERVER_ERROR: StatusCode = StatusCode(500)
        public val NOT_IMPLEMENTED: StatusCode = StatusCode(501)
        public val BAD_GATEWAY: StatusCode = StatusCode(502)
        public val SERVICE_UNAVAILABLE: StatusCode = StatusCode(503)
        public val GATEWAY_TIMEOUT: StatusCode = StatusCode(504)
        public val HTTP_VERSION_NOT_SUPPORTED: StatusCode = StatusCode(505)
        public val VARIANT_ALSO_NEGOTIATES: StatusCode = StatusCode(506)
        public val INSUFFICIENT_STORAGE: StatusCode = StatusCode(507)
        public val LOOP_DETECTED: StatusCode = StatusCode(508)
        public val NOT_EXTENDED: StatusCode = StatusCode(510)
        public val NETWORK_AUTHENTICATION_REQUIRED: StatusCode = StatusCode(511)

        public fun fromU16(code: UShort): StatusCode = StatusCode(code)

        public fun fromU16(code: Int): StatusCode = StatusCode(code.toUShort())

        public fun fromInt(code: Int): StatusCode = StatusCode(code.toUShort())
    }
}
