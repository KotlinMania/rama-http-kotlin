// port-lint: source rama-http/src/types/error.rs
package io.github.kotlinmania.ramahttp.types

public open class HttpError(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {
    public class InvalidUri(
        message: String,
    ) : HttpError("Invalid URI: $message")

    public class InvalidHeader(
        message: String,
    ) : HttpError("Invalid Header: $message")

    public class InvalidMethod(
        message: String,
    ) : HttpError("Invalid Method: $message")

    public class InvalidStatusCode(
        code: Int,
    ) : HttpError("Invalid Status Code: $code")

    public class BodyError(
        message: String,
        cause: Throwable? = null,
    ) : HttpError("Body error: $message", cause)

    public class ProtocolError(
        message: String,
    ) : HttpError("Protocol error: $message")

    public class Timeout(
        message: String = "Request timed out",
    ) : HttpError(message)

    public class Custom(
        message: String,
        cause: Throwable? = null,
    ) : HttpError(message, cause)

    public companion object {
        public operator fun invoke(message: String, cause: Throwable? = null): HttpError =
            Custom(message, cause)
    }
}
