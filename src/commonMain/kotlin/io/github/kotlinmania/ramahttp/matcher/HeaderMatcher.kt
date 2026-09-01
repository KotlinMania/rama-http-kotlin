// port-lint: source matcher/header.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Request

public class HeaderMatcher private constructor(
    private val name: HeaderName,
    private val kind: Kind,
) : Matcher<Request> {
    private sealed class Kind {
        object Exists : Kind()

        class Is(
            val value: HeaderValue,
        ) : Kind()

        class Contains(
            val value: HeaderValue,
        ) : Kind()
    }

    override fun matches(ext: Extensions?, req: Request): Boolean {
        ext?.hashCode()
        val headers = req.headers
        return when (val k = kind) {
            is Kind.Exists -> headers.containsKey(name)
            is Kind.Is -> headers.get(name) == k.value
            is Kind.Contains -> headers.getAll(name).any { it == k.value }
        }
    }

    public companion object {
        public fun exists(name: HeaderName): HeaderMatcher =
            HeaderMatcher(name, Kind.Exists)

        public fun `is`(name: HeaderName, value: HeaderValue): HeaderMatcher =
            HeaderMatcher(name, Kind.Is(value))

        public fun contains(name: HeaderName, value: HeaderValue): HeaderMatcher =
            HeaderMatcher(name, Kind.Contains(value))
    }
}
