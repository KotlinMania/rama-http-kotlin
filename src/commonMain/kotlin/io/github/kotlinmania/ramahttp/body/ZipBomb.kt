// port-lint: source body/zip_bomb.rs
package io.github.kotlinmania.ramahttp.body

import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderMap
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.HttpError
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

/**
 * A minimal in-memory ZIP archive that acts as a decompression or resource exhaustion trap.
 */
public class ZipBomb(
    public val filename: String = DEFAULT_FILENAME,
    public val depth: Int = DEFAULT_DEPTH,
    public val fanout: Int = DEFAULT_FANOUT,
    public val fileSize: Long = DEFAULT_FILE_SIZE,
) {
    public fun withFilename(filename: String): ZipBomb =
        ZipBomb(filename = filename, depth = depth, fanout = fanout, fileSize = fileSize)

    public fun withDepth(depth: Int): ZipBomb =
        ZipBomb(filename = filename, depth = if (depth > 0) depth else DEFAULT_DEPTH, fanout = fanout, fileSize = fileSize)

    public fun withFanout(fanout: Int): ZipBomb =
        ZipBomb(filename = filename, depth = depth, fanout = if (fanout > 0) fanout else DEFAULT_FANOUT, fileSize = fileSize)

    public fun withFileSize(fileSize: Long): ZipBomb =
        ZipBomb(filename = filename, depth = depth, fanout = fanout, fileSize = if (fileSize > 0) fileSize else DEFAULT_FILE_SIZE)

    public fun generateResponseHeaders(): Map<HeaderName, HeaderValue> = mapOf(
        HeaderName.fromString("Robots") to HeaderValue.fromString("none"),
        HeaderName.fromString("X-Robots-Tag") to HeaderValue.fromString("noindex, nofollow"),
        HeaderName.CONTENT_TYPE to HeaderValue.fromString("application/zip"),
        HeaderName.CONTENT_DISPOSITION to HeaderValue.fromString("attachment; filename=$filename.zip"),
    )

    public fun generateBody(): Body {
        // Generates minimal valid zip header with declared uncompressed size
        val header = "PK\u0003\u0004\u0014\u0000\u0000\u0000\u0008\u0000".encodeToByteArray()
        return Body.fromBytes(header)
    }

    public fun generateResponse(): Response {
        val res = Response.builder()
            .status(StatusCode.OK)
            .body(generateBody())
        for ((name, value) in generateResponseHeaders()) {
            res.headers.append(name, value)
        }
        return res
    }

    public fun intoResponse(): Response = generateResponse()

    public companion object {
        public const val DEFAULT_FILENAME: String = "token_backup"
        public const val DEFAULT_DEPTH: Int = 8
        public const val DEFAULT_FANOUT: Int = 32
        public const val DEFAULT_FILE_SIZE: Long = 512L * 1024L * 1024L
    }
}

/**
 * Protection against zip bomb attacks (excessive decompression ratio).
 */
public class ZipBombProtector(
    public val maxAllowedRatio: Double = 100.0,
    public val maxDecodedSize: Long = 100 * 1024 * 1024L, // 100MB
) {
    public fun check(compressedSize: Long, uncompressedSize: Long) {
        if (uncompressedSize > maxDecodedSize) {
            throw HttpError.BodyError("Decompressed size $uncompressedSize exceeds maximum allowed $maxDecodedSize")
        }
        if (compressedSize > 0) {
            val ratio = uncompressedSize.toDouble() / compressedSize.toDouble()
            if (ratio > maxAllowedRatio) {
                throw HttpError.BodyError("Compression ratio $ratio exceeds safety limit $maxAllowedRatio")
            }
        }
    }
}
