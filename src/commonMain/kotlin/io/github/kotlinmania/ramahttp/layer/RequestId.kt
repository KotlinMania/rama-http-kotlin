// port-lint: source layer/request_id.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
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
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return SetRequestId(inner, headerName, makeRequestId)
    }
}

internal class SetRequestId(
    private val inner: Service<Request, Response>,
    private val headerName: HeaderName = HeaderName.X_REQUEST_ID,
    private val makeRequestId: MakeRequestId,
) : Service<Request, Response> {

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
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return PropagateRequestId(inner, headerName)
    }
}

internal class PropagateRequestId(
    private val inner: Service<Request, Response>,
    private val headerName: HeaderName = HeaderName.X_REQUEST_ID,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        val reqId = req.headers.get(headerName) ?: req.extensions.get(RequestId::class)?.headerValue
        if (reqId != null && !res.headers.containsKey(headerName)) {
            res.headers.insert(headerName, reqId)
        }
        return res
    }
}
