// port-lint: source lib.rs
package io.github.kotlinmania.ramahttp.types

import io.github.kotlinmania.http.StatusCode as HttpStatusCode

/**
 * An HTTP status code (100-599), re-exported from `http-kotlin`.
 */
public typealias StatusCode = HttpStatusCode

public val StatusCode.code: UShort
    get() = asU16().toUShort()

public fun StatusCode.asInt(): Int = asU16()

public fun StatusCode.Companion.fromU16(code: UShort): StatusCode =
    fromU16(code.toInt()).getOrThrow()

public fun StatusCode.Companion.fromInt(code: Int): StatusCode =
    fromU16(code).getOrThrow()
