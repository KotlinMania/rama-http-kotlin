package io.github.kotlinmania.ramahttp.types

import io.github.kotlinmania.http.StatusCode
import io.github.kotlinmania.http.StatusCode as HttpStatusCode

public val StatusCode.code: UShort
    get() = asU16().toUShort()

public fun HttpStatusCode.Companion.fromU16(code: UShort): StatusCode =
    fromU16(code.toInt()).getOrThrow()

public fun HttpStatusCode.Companion.fromInt(code: Int): StatusCode =
    fromU16(code).getOrThrow()
