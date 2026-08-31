// port-lint: source rama-http/src/layer/timeout.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.intoResponse
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

public class TimeoutLayer(
    public val timeoutMillis: Long,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = TimeoutService(inner, timeoutMillis)
}

internal class TimeoutService(
    private val inner: HttpService,
    private val timeoutMillis: Long,
) : HttpService {
    override suspend fun serve(req: Request): Response =
        try {
            withTimeout(timeoutMillis) {
                inner.serve(req)
            }
        } catch (_: TimeoutCancellationException) {
            StatusCode.REQUEST_TIMEOUT.intoResponse()
        }
}
