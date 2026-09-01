// port-lint: source lib.rs
package io.github.kotlinmania.ramahttp.types

import io.github.kotlinmania.ramahttp.core.Extensions

public class RequestParts(
    public var method: Method = Method.GET,
    public var uri: Uri = Uri.fromString("/"),
    public var version: Version = Version.HTTP_11,
    public var headers: HeaderMap = HeaderMap(),
    public var extensions: Extensions = Extensions(),
)

public class Request(
    public var method: Method = Method.GET,
    public var uri: Uri = Uri.fromString("/"),
    public var version: Version = Version.HTTP_11,
    public val headers: HeaderMap = HeaderMap(),
    public val extensions: Extensions = Extensions(),
    public var body: Body = Body.empty(),
) {
    public fun intoBody(): Body = body

    public fun intoParts(): Pair<RequestParts, Body> {
        val parts = RequestParts(method, uri, version, headers.clone(), extensions.clone())
        return Pair(parts, body)
    }

    public companion object {
        public fun builder(): RequestBuilder = RequestBuilder()

        public fun new(body: Body): Request =
            Request(
                method = Method.GET,
                uri = Uri.fromString("/"),
                version = Version.HTTP_11,
                headers = HeaderMap(),
                extensions = Extensions(),
                body = body,
            )
    }
}

public class RequestBuilder {
    private var method: Method = Method.GET
    private var uri: Uri = Uri.fromString("/")
    private var version: Version = Version.HTTP_11
    private val headers: HeaderMap = HeaderMap()

    @PublishedApi
    internal val extensions: Extensions = Extensions()

    public fun method(method: Method): RequestBuilder {
        this.method = method
        return this
    }

    public fun method(method: String): RequestBuilder {
        this.method = Method.fromString(method)
        return this
    }

    public fun uri(uri: Uri): RequestBuilder {
        this.uri = uri
        return this
    }

    public fun uri(uri: String): RequestBuilder {
        this.uri = Uri.fromString(uri)
        return this
    }

    public fun version(version: Version): RequestBuilder {
        this.version = version
        return this
    }

    public fun header(name: HeaderName, value: HeaderValue): RequestBuilder {
        headers.append(name, value)
        return this
    }

    public fun header(name: String, value: String): RequestBuilder {
        headers.append(name, value)
        return this
    }

    public inline fun <reified T : Any> extension(value: T): RequestBuilder {
        extensions.insert(value)
        return this
    }

    public fun body(body: Body): Request =
        Request(
            method = method,
            uri = uri,
            version = version,
            headers = headers,
            extensions = extensions,
            body = body,
        )

    public fun body(text: String): Request = body(Body.fromString(text))

    public fun body(bytes: ByteArray): Request = body(Body.fromBytes(bytes))
}
