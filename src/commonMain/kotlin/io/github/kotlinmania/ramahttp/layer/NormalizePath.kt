// port-lint: source layer/normalize_path.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.Layer
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.PathAndQuery
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.Uri

public enum class NormalizeMode {
    TRIM,
    APPEND,
}

public class NormalizePathLayer(
    private val mode: NormalizeMode = NormalizeMode.TRIM,
) : Layer<Service<Request, Response>, Service<Request, Response>> {

    override fun layer(inner: Service<Request, Response>): Service<Request, Response> {
        return NormalizePath(inner, mode)
    }

    public companion object {
        public fun trimTrailingSlash(): NormalizePathLayer = NormalizePathLayer(NormalizeMode.TRIM)
        public fun appendTrailingSlash(): NormalizePathLayer = NormalizePathLayer(NormalizeMode.APPEND)
    }
}

internal class NormalizePath(
    private val inner: Service<Request, Response>,
    private val mode: NormalizeMode = NormalizeMode.TRIM,
) : Service<Request, Response> {

    override suspend fun serve(req: Request): Response {
        val uri = req.uri
        val path = uri.path
        val newPath = when (mode) {
            NormalizeMode.TRIM -> {
                if (path.length > 1 && path.endsWith('/')) {
                    path.trimEnd('/')
                } else {
                    path
                }
            }
            NormalizeMode.APPEND -> {
                if (!path.endsWith('/')) {
                    "$path/"
                } else {
                    path
                }
            }
        }

        if (newPath != path) {
            val query = uri.query
            val newPaq = if (query != null) "$newPath?$query" else newPath
            val parts = uri.intoParts()
            parts.pathAndQuery = PathAndQuery(newPaq)
            req.uri = Uri.fromParts(parts)
        }

        return inner.serve(req)
    }
}
