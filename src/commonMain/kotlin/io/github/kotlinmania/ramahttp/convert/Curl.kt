// port-lint: source convert/curl.rs
package io.github.kotlinmania.ramahttp.convert

import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.RequestParts
import io.github.kotlinmania.ramahttp.types.Version

public fun cmdStringForRequestParts(parts: RequestParts, payload: ByteArray? = null): String {
    val sb = StringBuilder("curl")
    val uri = parts.uri.toString()
    sb.append(" '$uri'")

    if (parts.headers.containsKey(HeaderName.ACCEPT_ENCODING)) {
        sb.append(" \\\n  --compressed")
    }

    if (parts.method != Method.GET) {
        sb.append(" \\\n  -X ").append(parts.method.asStr())
    }

    when (parts.version) {
        Version.HTTP_09 -> sb.append(" \\\n  --http0.9")
        Version.HTTP_10 -> sb.append(" \\\n  --http1.0")
        Version.HTTP_11 -> sb.append(" \\\n  --http1.1")
        Version.HTTP_2 -> sb.append(" \\\n  --http2")
        Version.HTTP_3 -> sb.append(" \\\n  --http3")
    }

    for (header in parts.headers.keys()) {
        if (header == HeaderName.HOST || header == HeaderName.CONTENT_LENGTH || header == HeaderName.fromString("transfer-encoding")) {
            continue
        }
        for (value in parts.headers.getAll(header)) {
            sb.append(" \\\n  -H '").append(header.asStr()).append(": ").append(value.toStr()).append("'")
        }
    }

    if (payload != null && payload.isNotEmpty()) {
        sb.append(" \\\n  --data-raw '").append(payload.decodeToString()).append("'")
    }

    return sb.toString()
}

public fun cmdStringForRequest(request: Request, payload: ByteArray? = null): String {
    val (parts, _) = request.intoParts()
    return cmdStringForRequestParts(parts, payload)
}
