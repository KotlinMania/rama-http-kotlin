// port-lint: source layer/request_id.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public class RequestId(
    public val headerValue: HeaderValue,
) {
    override fun toString(): String = headerValue.toStr()

    public companion object {
        public fun fromString(str: String): RequestId = RequestId(HeaderValue.fromString(str))
    }
}

public fun interface MakeRequestId {
    public fun makeRequestId(request: Request): RequestId?
}

public class SetRequestIdLayer(
    private val headerName: HeaderName = HeaderName.X_REQUEST_ID,
    private val makeRequestId: MakeRequestId,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = SetRequestId(inner, headerName, makeRequestId)
}

internal class SetRequestId(
    private val inner: HttpService,
    private val headerName: HeaderName = HeaderName.X_REQUEST_ID,
    private val makeRequestId: MakeRequestId,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        val reqId = makeRequestId.makeRequestId(req)
        if (reqId != null) {
            req.headers.insert(headerName, reqId.headerValue)
            req.extensions.insert(reqId)
        }
        return inner.serve(req)
    }
}

public class PropagateRequestIdLayer(
    private val headerName: HeaderName = HeaderName.X_REQUEST_ID,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = PropagateRequestId(inner, headerName)
}

internal class PropagateRequestId(
    private val inner: HttpService,
    private val headerName: HeaderName = HeaderName.X_REQUEST_ID,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        val reqId = req.headers.get(headerName) ?: req.extensions.get(RequestId::class)?.headerValue
        if (reqId != null && !res.headers.containsKey(headerName)) {
            res.headers.insert(headerName, reqId)
        }
        return res
    }
}
