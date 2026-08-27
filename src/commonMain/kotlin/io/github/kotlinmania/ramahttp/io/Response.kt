// port-lint: source io/response.rs
package io.github.kotlinmania.ramahttp.io

import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.Version

public suspend fun writeHttpResponse(
    response: Response,
    writeHeaders: Boolean = true,
    writeBody: Boolean = true,
): ByteArray {
    val sb = StringBuilder()
    if (writeHeaders) {
        val versionStr =
            when (response.version) {
                Version.HTTP_09 -> "HTTP/0.9"
                Version.HTTP_10 -> "HTTP/1.0"
                Version.HTTP_11 -> "HTTP/1.1"
                Version.HTTP_2 -> "HTTP/2.0"
                Version.HTTP_3 -> "HTTP/3.0"
            }
        val reason = response.status.canonicalReason()?.let { " $it" } ?: ""
        sb
            .append(versionStr)
            .append(" ")
            .append(response.status.asU16())
            .append(reason)
            .append("\r\n")

        for (name in response.headers.keys()) {
            for (value in response.headers.getAll(name)) {
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

    val bodyBytes = response.body.collect()
    return headerBytes + bodyBytes
}
