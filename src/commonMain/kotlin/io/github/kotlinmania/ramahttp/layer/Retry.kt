// port-lint: source rama-http/src/layer/retry/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public fun interface RetryPolicy {
    public fun shouldRetry(req: Request, res: Response, attempt: Int): Boolean
}

public class RetryLayer(
    public val maxRetries: Int = 3,
    public val policy: RetryPolicy = RetryPolicy { _, res, _ -> res.status.isServerError() },
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = RetryService(inner, maxRetries, policy)
}

internal class RetryService(
    private val inner: HttpService,
    private val maxRetries: Int = 3,
    private val policy: RetryPolicy = RetryPolicy { _, res, _ -> res.status.isServerError() },
) : HttpService {
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
