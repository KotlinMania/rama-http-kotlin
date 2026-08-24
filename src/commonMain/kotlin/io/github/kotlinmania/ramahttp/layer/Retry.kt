// port-lint: source layer/retry/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public fun interface RetryPolicy {
    public fun shouldRetry(req: Request, res: Response, attempt: Int): Boolean
}

public class RetryLayer(
    public val maxRetries: Int = 3,
    public val policy: RetryPolicy = RetryPolicy { _, res, _ -> res.status.isServerError() },
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return RetryService(inner, maxRetries, policy)
    }
}

internal class RetryService(
    private val inner: Service<Request, Response>,
    private val maxRetries: Int = 3,
    private val policy: RetryPolicy = RetryPolicy { _, res, _ -> res.status.isServerError() },
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        var attempt = 0
        while (true) {
            val res = inner.serve(req)
            attempt++
            if (attempt <= maxRetries && policy.shouldRetry(req, res, attempt)) {
                continue
            }
            return res
        }
    }
}
