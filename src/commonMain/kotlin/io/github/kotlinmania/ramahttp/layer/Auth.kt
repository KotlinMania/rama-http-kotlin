// port-lint: source layer/auth/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.intoResponse
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

public fun interface TokenValidator {
    public fun validate(token: String): Boolean
}

public class AddAuthorizationLayer(
    private val token: String,
    private val scheme: String = "Bearer",
) : Layer<Service<Request, Response>, Service<Request, Response>> {
    override fun layer(inner: Service<Request, Response>): Service<Request, Response> = AddAuthorizationService(inner, "$scheme $token")

    public companion object {
        public fun bearer(token: String): AddAuthorizationLayer = AddAuthorizationLayer(token, "Bearer")

        public fun basic(token: String): AddAuthorizationLayer = AddAuthorizationLayer(token, "Basic")
    }
}

internal class AddAuthorizationService(
    private val inner: Service<Request, Response>,
    private val authHeaderValue: String,
) : Service<Request, Response> {
    override suspend fun serve(req: Request): Response {
        req.headers.insert(HeaderName.AUTHORIZATION, HeaderValue.fromString(authHeaderValue))
        return inner.serve(req)
    }
}

public class ValidateAuthorizationLayer(
    private val scheme: String = "Bearer",
    private val validator: TokenValidator,
) : Layer<Service<Request, Response>, Service<Request, Response>> {
    public constructor(validator: TokenValidator) : this("Bearer", validator)

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> = ValidateAuthorizationService(inner, scheme, validator)
}

internal class ValidateAuthorizationService(
    private val inner: Service<Request, Response>,
    private val scheme: String,
    private val validator: TokenValidator,
) : Service<Request, Response> {
    override suspend fun serve(req: Request): Response {
        val authHeader = req.headers.get(HeaderName.AUTHORIZATION)?.toStrOrNull()
        if (authHeader == null) {
            return StatusCode.UNAUTHORIZED.intoResponse()
        }

        val prefix = "$scheme "
        if (!authHeader.startsWith(prefix, ignoreCase = true)) {
            return StatusCode.UNAUTHORIZED.intoResponse()
        }

        val token = authHeader.substring(prefix.length).trim()
        if (!validator.validate(token)) {
            return StatusCode.UNAUTHORIZED.intoResponse()
        }

        return inner.serve(req)
    }
}
