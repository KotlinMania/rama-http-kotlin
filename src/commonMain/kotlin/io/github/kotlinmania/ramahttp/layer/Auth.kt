// port-lint: source layer/auth/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
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
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService =
        AddAuthorizationService(inner, "$scheme $token")

    public companion object {
        public fun bearer(token: String): AddAuthorizationLayer = AddAuthorizationLayer(token, "Bearer")

        public fun basic(token: String): AddAuthorizationLayer = AddAuthorizationLayer(token, "Basic")
    }
}

internal class AddAuthorizationService(
    private val inner: HttpService,
    private val authHeaderValue: String,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        req.headers.insert(HeaderName.AUTHORIZATION, HeaderValue.fromString(authHeaderValue))
        return inner.serve(req)
    }
}

public class ValidateAuthorizationLayer(
    private val scheme: String = "Bearer",
    private val validator: TokenValidator,
) : HttpLayer {
    public constructor(validator: TokenValidator) : this("Bearer", validator)

    override fun layer(inner: HttpService): HttpService =
        ValidateAuthorizationService(inner, scheme, validator)
}

internal class ValidateAuthorizationService(
    private val inner: HttpService,
    private val scheme: String,
    private val validator: TokenValidator,
) : HttpService {
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
