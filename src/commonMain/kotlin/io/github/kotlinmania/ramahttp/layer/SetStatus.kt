// port-lint: source layer/set_status.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

public class SetStatusLayer(
    public val status: StatusCode,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return SetStatus(inner, status)
    }

    public companion object {
        public fun ok(): SetStatusLayer = SetStatusLayer(StatusCode.OK)
    }
}

internal class SetStatus(
    private val inner: Service<Request, Response>,
    private val status: StatusCode,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        res.status = status
        return res
    }

    public companion object {
        public fun ok(inner: Service<Request, Response>): SetStatus =
            SetStatus(inner, StatusCode.OK)
    }
}
