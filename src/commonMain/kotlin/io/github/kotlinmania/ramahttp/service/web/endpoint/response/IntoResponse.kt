// port-lint: source rama-http/src/service/web/endpoint/response/into_response_parts.rs
package io.github.kotlinmania.ramahttp.service.web.endpoint.response

import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

/**
 * Trait/Interface for types that can be converted into an HTTP [Response].
 */
public fun interface IntoResponse {
    public fun intoResponse(): Response
}

/**
 * HTML Response wrapper.
 */
public data class Html(
    public val content: String,
) : IntoResponse {
    override fun intoResponse(): Response =
        Response
            .builder()
            .status(StatusCode.OK)
            .header(HeaderName.CONTENT_TYPE, HeaderValue("text/html; charset=utf-8"))
            .body(Body.fromString(content))
}

/**
 * JSON Response wrapper.
 */
public data class Json(
    public val jsonString: String,
) : IntoResponse {
    override fun intoResponse(): Response =
        Response
            .builder()
            .status(StatusCode.OK)
            .header(HeaderName.CONTENT_TYPE, HeaderValue("application/json; charset=utf-8"))
            .body(Body.fromString(jsonString))
}

/**
 * Text Response wrapper.
 */
public data class Text(
    public val text: String,
) : IntoResponse {
    override fun intoResponse(): Response =
        Response
            .builder()
            .status(StatusCode.OK)
            .header(HeaderName.CONTENT_TYPE, HeaderValue("text/plain; charset=utf-8"))
            .body(Body.fromString(text))
}

public fun StatusCode.intoResponse(): Response =
    Response
        .builder()
        .status(this)
        .body(Body.empty())
