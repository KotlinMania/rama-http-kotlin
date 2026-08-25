// port-lint: source utils/header_value.rs
package io.github.kotlinmania.ramahttp.utils

import io.github.kotlinmania.ramahttp.types.HeaderMap
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public sealed class HeaderValueErr(
    message: String,
) : Exception(message) {
    public class HeaderMissing(
        public val key: String,
    ) : HeaderValueErr("header missing: $key")

    public class HeaderInvalid(
        public val key: String,
    ) : HeaderValueErr("header invalid: $key")
}

public interface HeaderValueGetter {
    public fun headerStr(key: HeaderName): Result<String>

    public fun headerStr(key: String): Result<String> = headerStr(HeaderName.fromString(key))

    public fun headerBytes(key: HeaderName): Result<ByteArray>

    public fun headerBytes(key: String): Result<ByteArray> = headerBytes(HeaderName.fromString(key))
}

public fun HeaderMap.headerStr(key: HeaderName): Result<String> {
    val value = get(key) ?: return Result.failure(HeaderValueErr.HeaderMissing(key.asStr()))
    val str = value.toStrOrNull() ?: return Result.failure(HeaderValueErr.HeaderInvalid(key.asStr()))
    return Result.success(str)
}

public fun HeaderMap.headerStr(key: String): Result<String> = headerStr(HeaderName.fromString(key))

public fun HeaderMap.headerBytes(key: HeaderName): Result<ByteArray> {
    val value = get(key) ?: return Result.failure(HeaderValueErr.HeaderMissing(key.asStr()))
    return Result.success(value.asBytes())
}

public fun HeaderMap.headerBytes(key: String): Result<ByteArray> = headerBytes(HeaderName.fromString(key))

public fun Request.headerStr(key: HeaderName): Result<String> = headers.headerStr(key)

public fun Request.headerStr(key: String): Result<String> = headers.headerStr(key)

public fun Request.headerBytes(key: HeaderName): Result<ByteArray> = headers.headerBytes(key)

public fun Request.headerBytes(key: String): Result<ByteArray> = headers.headerBytes(key)

public fun Response.headerStr(key: HeaderName): Result<String> = headers.headerStr(key)

public fun Response.headerStr(key: String): Result<String> = headers.headerStr(key)

public fun Response.headerBytes(key: HeaderName): Result<ByteArray> = headers.headerBytes(key)

public fun Response.headerBytes(key: String): Result<ByteArray> = headers.headerBytes(key)
