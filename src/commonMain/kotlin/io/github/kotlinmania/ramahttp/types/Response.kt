// port-lint: source types/response.rs
package io.github.kotlinmania.ramahttp.types

import io.github.kotlinmania.ramahttp.core.Extensions

public class ResponseParts(
    public var status: StatusCode = StatusCode.OK,
    public var version: Version = Version.HTTP_11,
    public var headers: HeaderMap = HeaderMap(),
    public var extensions: Extensions = Extensions(),
)

public class Response(
    public var status: StatusCode = StatusCode.OK,
    public var version: Version = Version.HTTP_11,
    public val headers: HeaderMap = HeaderMap(),
    public val extensions: Extensions = Extensions(),
    public var body: Body = Body.empty(),
) {
    public fun intoBody(): Body = body

    public fun intoParts(): Pair<ResponseParts, Body> {
        val parts = ResponseParts(status, version, headers.clone(), extensions.clone())
        return Pair(parts, body)
    }

    public companion object {
        public fun builder(): ResponseBuilder = ResponseBuilder()

        public fun new(body: Body): Response =
            Response(
                status = StatusCode.OK,
                version = Version.HTTP_11,
                headers = HeaderMap(),
                extensions = Extensions(),
                body = body,
            )

        public fun default(): Response = new(Body.empty())
    }
}

public class ResponseBuilder {
    private var status: StatusCode = StatusCode.OK
    private var version: Version = Version.HTTP_11
    private val headers: HeaderMap = HeaderMap()

    @PublishedApi
    internal val extensions: Extensions = Extensions()

    public fun status(status: StatusCode): ResponseBuilder {
        this.status = status
        return this
    }

    public fun status(status: Int): ResponseBuilder {
        this.status = StatusCode.fromInt(status)
        return this
    }

    public fun version(version: Version): ResponseBuilder {
        this.version = version
        return this
    }

    public fun header(name: HeaderName, value: HeaderValue): ResponseBuilder {
        headers.append(name, value)
        return this
    }

    public fun header(name: String, value: String): ResponseBuilder {
        headers.append(name, value)
        return this
    }

    public inline fun <reified T : Any> extension(value: T): ResponseBuilder {
        extensions.insert(value)
        return this
    }

    public fun body(body: Body): Response =
        Response(
            status = status,
            version = version,
            headers = headers,
            extensions = extensions,
            body = body,
        )

    public fun body(text: String): Response = body(Body.fromString(text))

    public fun body(bytes: ByteArray): Response = body(Body.fromBytes(bytes))
}
