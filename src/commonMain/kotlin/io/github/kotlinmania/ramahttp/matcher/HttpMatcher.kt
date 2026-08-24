// port-lint: source matcher/mod.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Version

public class HttpMatcher private constructor(
    private val kind: Kind,
    private val negated: Boolean = false,
) : io.github.kotlinmania.ramahttp.core.RequestMatcher {

    private sealed class Kind {
        class All(val matchers: List<HttpMatcher>) : Kind()
        class Any(val matchers: List<HttpMatcher>) : Kind()
        class Method(val matcher: MethodMatcher) : Kind()
        class Path(val matcher: PathMatcher) : Kind()
        class Domain(val matcher: DomainMatcher) : Kind()
        class Ver(val matcher: VersionMatcher) : Kind()
        class Uri(val matcher: UriMatcher) : Kind()
        class Header(val matcher: HeaderMatcher) : Kind()
        class SubdomainTrie(val matcher: SubdomainTrieMatcher) : Kind()
        class Custom(val matcher: io.github.kotlinmania.ramahttp.core.RequestMatcher) : Kind()
    }

    public fun negate(): HttpMatcher = HttpMatcher(kind, !negated)

    public fun and(other: HttpMatcher): HttpMatcher {
        val list = when (kind) {
            is Kind.All -> kind.matchers + other
            else -> listOf(this, other)
        }
        return HttpMatcher(Kind.All(list))
    }

    public fun or(other: HttpMatcher): HttpMatcher {
        val list = when (kind) {
            is Kind.Any -> kind.matchers + other
            else -> listOf(this, other)
        }
        return HttpMatcher(Kind.Any(list))
    }

    public fun andMethod(method: MethodMatcher): HttpMatcher = and(method(method))
    public fun andPath(path: String): HttpMatcher = and(path(path))
    public fun andDomain(domain: String): HttpMatcher = and(domain(domain))
    public fun andHeader(header: HeaderMatcher): HttpMatcher = and(header(header))

    override fun matches(ext: Extensions?, req: Request): Boolean {
        val matched = when (val k = kind) {
            is Kind.All -> k.matchers.all { it.matches(ext, req) }
            is Kind.Any -> k.matchers.any { it.matches(ext, req) }
            is Kind.Method -> k.matcher.matches(ext, req)
            is Kind.Path -> k.matcher.matches(ext, req)
            is Kind.Domain -> k.matcher.matches(ext, req)
            is Kind.Ver -> k.matcher.matches(ext, req)
            is Kind.Uri -> k.matcher.matches(ext, req)
            is Kind.Header -> k.matcher.matches(ext, req)
            is Kind.SubdomainTrie -> k.matcher.matches(ext, req)
            is Kind.Custom -> k.matcher.matches(ext, req)
        }
        return if (negated) !matched else matched
    }

    public companion object {
        public fun all(vararg matchers: HttpMatcher): HttpMatcher =
            HttpMatcher(Kind.All(matchers.toList()))

        public fun any(vararg matchers: HttpMatcher): HttpMatcher =
            HttpMatcher(Kind.Any(matchers.toList()))

        public fun method(matcher: MethodMatcher): HttpMatcher =
            HttpMatcher(Kind.Method(matcher))

        public fun methodGet(): HttpMatcher = method(MethodMatcher.GET)
        public fun methodPost(): HttpMatcher = method(MethodMatcher.POST)
        public fun methodPut(): HttpMatcher = method(MethodMatcher.PUT)
        public fun methodDelete(): HttpMatcher = method(MethodMatcher.DELETE)
        public fun methodHead(): HttpMatcher = method(MethodMatcher.HEAD)
        public fun methodOptions(): HttpMatcher = method(MethodMatcher.OPTIONS)
        public fun methodPatch(): HttpMatcher = method(MethodMatcher.PATCH)
        public fun methodTrace(): HttpMatcher = method(MethodMatcher.TRACE)
        public fun methodConnect(): HttpMatcher = method(MethodMatcher.CONNECT)

        public fun path(path: String): HttpMatcher =
            HttpMatcher(Kind.Path(PathMatcher.new(path)))

        public fun pathPrefix(path: String): HttpMatcher =
            HttpMatcher(Kind.Path(PathMatcher.newPrefix(path)))

        public fun domain(domain: String): HttpMatcher =
            HttpMatcher(Kind.Domain(DomainMatcher.auto(domain)))

        public fun version(version: Version): HttpMatcher {
            val vm = VersionMatcher.fromVersion(version) ?: VersionMatcher.HTTP_11
            return HttpMatcher(Kind.Ver(vm))
        }

        public fun header(matcher: HeaderMatcher): HttpMatcher =
            HttpMatcher(Kind.Header(matcher))

        public fun headerExists(name: HeaderName): HttpMatcher =
            header(HeaderMatcher.exists(name))

        public fun headerIs(name: HeaderName, value: HeaderValue): HttpMatcher =
            header(HeaderMatcher.`is`(name, value))

        public fun headerContains(name: HeaderName, value: HeaderValue): HttpMatcher =
            header(HeaderMatcher.contains(name, value))

        public fun custom(matcher: io.github.kotlinmania.ramahttp.core.RequestMatcher): HttpMatcher =
            HttpMatcher(Kind.Custom(matcher))

        public fun custom(matcher: Matcher<Request>): HttpMatcher =
            HttpMatcher(Kind.Custom(io.github.kotlinmania.ramahttp.core.RequestMatcher { ext, req -> matcher.matches(ext, req) }))
    }
}
