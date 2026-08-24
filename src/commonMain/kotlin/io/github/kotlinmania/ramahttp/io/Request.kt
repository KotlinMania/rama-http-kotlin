// port-lint: source io/request.rs
package io.github.kotlinmania.ramahttp.io

import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Version

public suspend fun writeHttpRequest(
    request: Request,
    writeHeaders: Boolean = true,
    writeBody: Boolean = true,
): ByteArray {
    val sb = StringBuilder()
    if (writeHeaders) {
        val path = request.uri.pathAndQuery?.asStr ?: "/"
        val versionStr =
            when (request.version) {
                Version.HTTP_09 -> "HTTP/0.9"
                Version.HTTP_10 -> "HTTP/1.0"
                Version.HTTP_11 -> "HTTP/1.1"
                Version.HTTP_2 -> "HTTP/2.0"
                Version.HTTP_3 -> "HTTP/3.0"
            }
        sb
            .append(request.method.asStr())
            .append(" ")
            .append(path)
            .append(" ")
            .append(versionStr)
            .append("\r\n")

        for (name in request.headers.keys()) {
            for (value in request.headers.getAll(name)) {
                sb
                    .append(name.asStr())
                    .append(": ")
                    .append(value.toStr())
                    .append("\r\n")
            }
        }
        sb.append("\r\n")
    }

    val headerBytes = sb.toString().encodeToByteArray()
    if (!writeBody) {
        return headerBytes
    }

    val bodyBytes = request.body.collect()
    return headerBytes + bodyBytes
}
