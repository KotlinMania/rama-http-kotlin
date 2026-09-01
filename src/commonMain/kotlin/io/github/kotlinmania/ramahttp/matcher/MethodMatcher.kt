// port-lint: source matcher/method.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request

/**
 * A matcher that matches one or more HTTP methods.
 */
public class MethodMatcher private constructor(
    public val bits: UShort,
) : Matcher<Request> {
    public fun or(other: MethodMatcher): MethodMatcher = MethodMatcher((this.bits or other.bits))

    public fun and(other: MethodMatcher): MethodMatcher = MethodMatcher((this.bits and other.bits))

    public fun contains(other: MethodMatcher): Boolean = (this.bits and other.bits) == other.bits

    override fun matches(ext: Extensions?, req: Request): Boolean {
        ext?.hashCode()
        val methodMatcher = fromMethod(req.method) ?: return false
        return contains(methodMatcher)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MethodMatcher) return false
        return bits == other.bits
    }

    override fun hashCode(): Int = bits.hashCode()

    public companion object {
        public val CONNECT: MethodMatcher = MethodMatcher((1 shl 1).toUShort())
        public val DELETE: MethodMatcher = MethodMatcher((1 shl 2).toUShort())
        public val GET: MethodMatcher = MethodMatcher((1 shl 3).toUShort())
        public val HEAD: MethodMatcher = MethodMatcher((1 shl 4).toUShort())
        public val OPTIONS: MethodMatcher = MethodMatcher((1 shl 5).toUShort())
        public val PATCH: MethodMatcher = MethodMatcher((1 shl 6).toUShort())
        public val POST: MethodMatcher = MethodMatcher((1 shl 7).toUShort())
        public val PUT: MethodMatcher = MethodMatcher((1 shl 8).toUShort())
        public val TRACE: MethodMatcher = MethodMatcher((1 shl 9).toUShort())

        public fun fromBits(bits: UShort): MethodMatcher = MethodMatcher(bits)

        public fun fromMethod(method: Method): MethodMatcher? =
            when (method) {
                Method.CONNECT -> CONNECT
                Method.DELETE -> DELETE
                Method.GET -> GET
                Method.HEAD -> HEAD
                Method.OPTIONS -> OPTIONS
                Method.PATCH -> PATCH
                Method.POST -> POST
                Method.PUT -> PUT
                Method.TRACE -> TRACE
                else -> {
                    when (method.asStr().uppercase()) {
                        "CONNECT" -> CONNECT
                        "DELETE" -> DELETE
                        "GET" -> GET
                        "HEAD" -> HEAD
                        "OPTIONS" -> OPTIONS
                        "PATCH" -> PATCH
                        "POST" -> POST
                        "PUT" -> PUT
                        "TRACE" -> TRACE
                        else -> null
                    }
                }
            }
    }
}
