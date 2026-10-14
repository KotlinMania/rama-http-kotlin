// port-lint: source matcher/domain.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramacore.Extensions
import io.github.kotlinmania.ramacore.matcher.Matcher
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramanet.address.Domain

public class DomainMatcher private constructor(
    private val domain: Domain,
    private val sub: Boolean,
) : Matcher<Request> {
    public fun isMatch(host: String): Boolean {
        val cleanHost = host.trim().lowercase().removePrefix(".")
        if (!Domain.isValidName(cleanHost)) {
            return false
        }
        val parsed = Domain.parse(cleanHost)
        return if (sub) {
            domain.isParentOf(parsed)
        } else {
            domain == parsed
        }
    }

    override fun matches(ext: Extensions?, req: Request): Boolean {
        ext?.hashCode()
        val host =
            req.uri.host
                ?: req.headers
                    .get(HeaderName.HOST)
                    ?.toStrOrNull()
                    ?.substringBefore(':')
                ?: return false
        return isMatch(host)
    }

    public companion object {
        public fun exact(domain: Domain): DomainMatcher = DomainMatcher(domain, sub = false)

        public fun exact(domain: String): DomainMatcher =
            DomainMatcher(Domain.parse(domain.trim().removePrefix(".")), sub = false)

        public fun sub(domain: Domain): DomainMatcher = DomainMatcher(domain, sub = true)

        public fun sub(domain: String): DomainMatcher =
            DomainMatcher(Domain.parse(domain.trim().removePrefix(".")), sub = true)

        public fun auto(domain: String): DomainMatcher {
            val trimmed = domain.trim()
            return if (trimmed.startsWith("*.")) {
                sub(trimmed.substring(2))
            } else if (trimmed.startsWith(".")) {
                sub(trimmed.substring(1))
            } else {
                exact(trimmed)
            }
        }
    }
}
