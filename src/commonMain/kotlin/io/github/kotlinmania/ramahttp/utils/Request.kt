// port-lint: source utils/request.rs
package io.github.kotlinmania.ramahttp.utils

import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Uri

/**
 * Get the URI as complete as possible for the given request.
 */
public fun requestUri(req: Request): Uri {
    val uri = req.uri
    if (uri.scheme != null && uri.authority != null) {
        return uri
    }

    val hostHeader = req.headers.get(HeaderName.HOST)?.toStrOrNull()
    if (hostHeader != null) {
        val paq = uri.pathAndQuery?.asStr ?: "/"
        val pathStr = if (paq.startsWith("/")) paq else "/$paq"
        return Uri.fromString("http://$hostHeader$pathStr")
    }

    return uri
}
