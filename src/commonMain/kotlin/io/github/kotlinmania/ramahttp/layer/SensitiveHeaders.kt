// port-lint: source layer/sensitive_headers.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public class SensitiveHeaders(
    public val sensitiveHeaders: Set<HeaderName>,
) {
    public fun isSensitive(name: HeaderName): Boolean = sensitiveHeaders.contains(name)

    public companion object {
        public val DEFAULT_SENSITIVE_HEADERS: Set<HeaderName> = setOf(
            HeaderName.AUTHORIZATION,
            HeaderName.COOKIE,
            HeaderName.SET_COOKIE,
            HeaderName.PROXY_AUTHORIZATION,
        )
    }
}

public class SensitiveHeadersLayer(
    private val sensitiveHeaders: Set<HeaderName> = SensitiveHeaders.DEFAULT_SENSITIVE_HEADERS,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return SensitiveHeadersService(inner, sensitiveHeaders)
    }
}

internal class SensitiveHeadersService(
    private val inner: Service<Request, Response>,
    private val sensitiveHeaders: Set<HeaderName> = SensitiveHeaders.DEFAULT_SENSITIVE_HEADERS,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        req.extensions.insert(SensitiveHeaders(sensitiveHeaders))
        val res = inner.serve(req)
        res.extensions.insert(SensitiveHeaders(sensitiveHeaders))
        return res
    }
}
