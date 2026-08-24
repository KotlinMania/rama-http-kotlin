// port-lint: source layer/cors/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

public class CorsLayer : Layer<Service<Request, Response>, Service<Request, Response>> {
    private var allowOrigin: HeaderValue? = null
    private var allowMethods: List<Method>? = null
    private var allowHeaders: List<HeaderName>? = null
    private var exposeHeaders: List<HeaderName>? = null
    private var allowCredentials: Boolean = false
    private var maxAge: Long? = null

    public fun allowOrigin(origin: HeaderValue): CorsLayer {
        this.allowOrigin = origin
        return this
    }

    public fun allowOrigin(origin: String): CorsLayer {
        this.allowOrigin = HeaderValue.fromString(origin)
        return this
    }

    public fun allowAnyOrigin(): CorsLayer {
        this.allowOrigin = HeaderValue.fromString("*")
        return this
    }

    public fun allowMethods(methods: List<Method>): CorsLayer {
        this.allowMethods = methods
        return this
    }

    public fun allowAnyMethod(): CorsLayer {
        this.allowMethods = listOf(
            Method.GET,
            Method.POST,
            Method.PUT,
            Method.DELETE,
            Method.PATCH,
            Method.HEAD,
            Method.OPTIONS,
        )
        return this
    }

    public fun allowHeaders(headers: List<HeaderName>): CorsLayer {
        this.allowHeaders = headers
        return this
    }

    public fun allowAnyHeader(): CorsLayer {
        this.allowHeaders = listOf(
            HeaderName.ACCEPT,
            HeaderName.AUTHORIZATION,
            HeaderName.CONTENT_TYPE,
            HeaderName.ORIGIN,
        )
        return this
    }

    public fun exposeHeaders(headers: List<HeaderName>): CorsLayer {
        this.exposeHeaders = headers
        return this
    }

    public fun allowCredentials(allow: Boolean): CorsLayer {
        this.allowCredentials = allow
        return this
    }

    public fun maxAge(seconds: Long): CorsLayer {
        this.maxAge = seconds
        return this
    }

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return CorsService(
            inner = inner,
            allowOrigin = allowOrigin,
            allowMethods = allowMethods,
            allowHeaders = allowHeaders,
            exposeHeaders = exposeHeaders,
            allowCredentials = allowCredentials,
            maxAge = maxAge,
        )
    }

    public companion object {
        public fun permissive(): CorsLayer =
            CorsLayer()
                .allowAnyOrigin()
                .allowAnyMethod()
                .allowAnyHeader()
    }
}

internal class CorsService(
    private val inner: Service<Request, Response>,
    private val allowOrigin: HeaderValue?,
    private val allowMethods: List<Method>?,
    private val allowHeaders: List<HeaderName>?,
    private val exposeHeaders: List<HeaderName>?,
    private val allowCredentials: Boolean,
    private val maxAge: Long?,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        val isPreflight = req.method == Method.OPTIONS && req.headers.containsKey(HeaderName.ACCESS_CONTROL_REQUEST_METHOD)

        if (isPreflight) {
            val res = Response.new(Body.empty())
            res.status = StatusCode.NO_CONTENT
            applyCorsHeaders(res)
            return res
        }

        val res = inner.serve(req)
        applyCorsHeaders(res)
        return res
    }

    private fun applyCorsHeaders(res: Response) {
        val headers = res.headers

        if (allowOrigin != null) {
            headers.insert(HeaderName.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin)
        }

        if (allowCredentials) {
            headers.insert(HeaderName.ACCESS_CONTROL_ALLOW_CREDENTIALS, HeaderValue.fromString("true"))
        }

        if (allowMethods != null && allowMethods.isNotEmpty()) {
            val methodsStr = allowMethods.joinToString(", ") { it.asStr() }
            headers.insert(HeaderName.ACCESS_CONTROL_ALLOW_METHODS, HeaderValue.fromString(methodsStr))
        }

        if (allowHeaders != null && allowHeaders.isNotEmpty()) {
            val headersStr = allowHeaders.joinToString(", ") { it.asStr() }
            headers.insert(HeaderName.ACCESS_CONTROL_ALLOW_HEADERS, HeaderValue.fromString(headersStr))
        }

        if (exposeHeaders != null && exposeHeaders.isNotEmpty()) {
            val exposeStr = exposeHeaders.joinToString(", ") { it.asStr() }
            headers.insert(HeaderName.ACCESS_CONTROL_EXPOSE_HEADERS, HeaderValue.fromString(exposeStr))
        }

        if (maxAge != null) {
            headers.insert(HeaderName.ACCESS_CONTROL_MAX_AGE, HeaderValue.fromString(maxAge.toString()))
        }
    }
}
