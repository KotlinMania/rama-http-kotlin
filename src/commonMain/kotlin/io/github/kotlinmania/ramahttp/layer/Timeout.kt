// port-lint: source layer/timeout.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.intoResponse
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

public class TimeoutLayer(
    public val timeoutMillis: Long,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return TimeoutService(inner, timeoutMillis)
    }
}

internal class TimeoutService(
    private val inner: Service<Request, Response>,
    private val timeoutMillis: Long,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        return try {
            withTimeout(timeoutMillis) {
                inner.serve(req)
            }
        } catch (_: TimeoutCancellationException) {
            StatusCode.REQUEST_TIMEOUT.intoResponse()
        }
    }
}
