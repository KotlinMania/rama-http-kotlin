// port-lint: source rama-http/src/service/redirect/mod.rs
package io.github.kotlinmania.ramahttp.service.redirect

import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

/**
 * Service that redirects all HTTP requests to HTTPS.
 */
public class RedirectHttpToHttps(
    public val statusCode: StatusCode = StatusCode.PERMANENT_REDIRECT,
    public val overwritePort: Int? = null,
    public val dropQuery: Boolean = false,
) : Service<Request, Response> {
    public fun statusMoved(): RedirectHttpToHttps =
        RedirectHttpToHttps(StatusCode.MOVED_PERMANENTLY, overwritePort, dropQuery)

    public fun statusFound(): RedirectHttpToHttps =
        RedirectHttpToHttps(StatusCode.FOUND, overwritePort, dropQuery)

    public fun statusOther(): RedirectHttpToHttps =
        RedirectHttpToHttps(StatusCode.SEE_OTHER, overwritePort, dropQuery)

    public fun statusTemporary(): RedirectHttpToHttps =
        RedirectHttpToHttps(StatusCode.TEMPORARY_REDIRECT, overwritePort, dropQuery)

    public fun overwritePort(port: Int?): RedirectHttpToHttps =
        RedirectHttpToHttps(statusCode, port, dropQuery)

    public fun dropQuery(drop: Boolean): RedirectHttpToHttps =
        RedirectHttpToHttps(statusCode, overwritePort, drop)

    override suspend fun serve(req: Request): Response {
        val originalUri = req.uri
        val scheme = "https"
        val host = originalUri.host ?: "localhost"
        val portPart = if (overwritePort != null) ":$overwritePort" else ""
        val path = if (dropQuery) originalUri.path else (originalUri.pathAndQuery?.asStr ?: originalUri.path)

        val redirectTarget = "$scheme://$host$portPart$path"

        return Response
            .builder()
            .status(statusCode)
            .header(HeaderName.LOCATION, HeaderValue.fromString(redirectTarget))
            .body(Body.empty())
    }

    public companion object {
        public fun new(): RedirectHttpToHttps = RedirectHttpToHttps()
    }
}

/**
 * Service that redirects all requests to a static location.
 */
public class RedirectStatic(
    public val location: String,
    public val statusCode: StatusCode = StatusCode.SEE_OTHER,
) : Service<Request, Response> {
    override suspend fun serve(req: Request): Response =
        Response
            .builder()
            .status(statusCode)
            .header(HeaderName.LOCATION, HeaderValue.fromString(location))
            .body(Body.empty())

    public companion object {
        public fun to(loc: String): RedirectStatic =
            RedirectStatic(loc, StatusCode.SEE_OTHER)

        public fun moved(loc: String): RedirectStatic =
            RedirectStatic(loc, StatusCode.MOVED_PERMANENTLY)

        public fun found(loc: String): RedirectStatic =
            RedirectStatic(loc, StatusCode.FOUND)

        public fun temporary(loc: String): RedirectStatic =
            RedirectStatic(loc, StatusCode.TEMPORARY_REDIRECT)

        public fun permanent(loc: String): RedirectStatic =
            RedirectStatic(loc, StatusCode.PERMANENT_REDIRECT)
    }
}
