// port-lint: source layer/set_header/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public class SetRequestHeaderLayer(
    public val name: HeaderName,
    public val value: HeaderValue,
    public val override: Boolean = true,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return SetRequestHeader(inner, name, value, override)
    }

    public companion object {
        public fun overriding(name: HeaderName, value: HeaderValue): SetRequestHeaderLayer =
            SetRequestHeaderLayer(name, value, override = true)

        public fun ifNotPresent(name: HeaderName, value: HeaderValue): SetRequestHeaderLayer =
            SetRequestHeaderLayer(name, value, override = false)
    }
}

internal class SetRequestHeader(
    private val inner: Service<Request, Response>,
    private val name: HeaderName,
    private val value: HeaderValue,
    private val override: Boolean = true,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        if (override || !req.headers.containsKey(name)) {
            req.headers.insert(name, value)
        }
        return inner.serve(req)
    }
}

public class SetResponseHeaderLayer(
    public val name: HeaderName,
    public val value: HeaderValue,
    public val override: Boolean = true,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return SetResponseHeader(inner, name, value, override)
    }

    public companion object {
        public fun overriding(name: HeaderName, value: HeaderValue): SetResponseHeaderLayer =
            SetResponseHeaderLayer(name, value, override = true)

        public fun ifNotPresent(name: HeaderName, value: HeaderValue): SetResponseHeaderLayer =
            SetResponseHeaderLayer(name, value, override = false)
    }
}

internal class SetResponseHeader(
    private val inner: Service<Request, Response>,
    private val name: HeaderName,
    private val value: HeaderValue,
    private val override: Boolean = true,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        val res = inner.serve(req)
        if (override || !res.headers.containsKey(name)) {
            res.headers.insert(name, value)
        }
        return res
    }
}
