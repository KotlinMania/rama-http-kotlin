// port-lint: source layer/sensitive_headers.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
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
) : HttpLayer {

    override fun layer(inner: HttpService): HttpService {
        return SensitiveHeadersService(inner, sensitiveHeaders)
    }
}

internal class SensitiveHeadersService(
    private val inner: HttpService,
    private val sensitiveHeaders: Set<HeaderName> = SensitiveHeaders.DEFAULT_SENSITIVE_HEADERS,
) : HttpService {

    override suspend fun serve(req: Request): Response {
        req.extensions.insert(SensitiveHeaders(sensitiveHeaders))
        val res = inner.serve(req)
        res.extensions.insert(SensitiveHeaders(sensitiveHeaders))
        return res
    }
}
