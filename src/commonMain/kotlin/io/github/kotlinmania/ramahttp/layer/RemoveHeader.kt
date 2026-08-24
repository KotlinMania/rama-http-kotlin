// port-lint: source layer/remove_header/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public class RemoveRequestHeaderLayer(
    public val name: HeaderName,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return RemoveRequestHeader(inner, name)
    }
}

internal class RemoveRequestHeader(
    private val inner: Service<Request, Response>,
    private val name: HeaderName,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        req.headers.remove(name)
        return inner.serve(req)
    }
}

public class RemoveResponseHeaderLayer(
    public val name: HeaderName,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return RemoveResponseHeader(inner, name)
    }
}

internal class RemoveResponseHeader(
    private val inner: Service<Request, Response>,
    private val name: HeaderName,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        res.headers.remove(name)
        return res
    }
}
