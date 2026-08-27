// port-lint: source layer/normalize_path.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
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
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService = NormalizePath(inner, mode)

    public companion object {
        public fun trimTrailingSlash(): NormalizePathLayer = NormalizePathLayer(NormalizeMode.TRIM)

        public fun appendTrailingSlash(): NormalizePathLayer = NormalizePathLayer(NormalizeMode.APPEND)
    }
}

internal class NormalizePath(
    private val inner: HttpService,
    private val mode: NormalizeMode = NormalizeMode.TRIM,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        val uri = req.uri
        val path = uri.path
        val newPath =
            when (mode) {
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
