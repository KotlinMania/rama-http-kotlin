// port-lint: source layer/set_status.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

public class SetStatusLayer(
    public val status: StatusCode,
) : HttpLayer {

    override fun layer(inner: HttpService): HttpService {
        return SetStatus(inner, status)
    }

    public companion object {
        public fun ok(): SetStatusLayer = SetStatusLayer(StatusCode.OK)
    }
}

internal class SetStatus(
    private val inner: HttpService,
    private val status: StatusCode,
) : HttpService {

    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        res.status = status
        return res
    }

    public companion object {
        public fun ok(inner: HttpService): SetStatus =
            SetStatus(inner, StatusCode.OK)
    }
}
