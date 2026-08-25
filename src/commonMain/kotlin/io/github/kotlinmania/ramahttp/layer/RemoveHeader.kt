// port-lint: source layer/remove_header/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public class RemoveRequestHeaderLayer(
    public val name: HeaderName,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = RemoveRequestHeader(inner, name)
}

internal class RemoveRequestHeader(
    private val inner: HttpService,
    private val name: HeaderName,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        req.headers.remove(name)
        return inner.serve(req)
    }
}

public class RemoveResponseHeaderLayer(
    public val name: HeaderName,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = RemoveResponseHeader(inner, name)
}

internal class RemoveResponseHeader(
    private val inner: HttpService,
    private val name: HeaderName,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        res.headers.remove(name)
        return res
    }
}
