// port-lint: source matcher/domain.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request

public class DomainMatcher private constructor(
    private val target: String,
    private val isSubdomain: Boolean,
) : Matcher<Request> {
    public fun isMatch(host: String): Boolean {
        val cleanHost = host.trim().lowercase().removePrefix(".")
        val cleanTarget = target.trim().lowercase().removePrefix(".")
        if (cleanHost == cleanTarget) {
            return true
        }
        if (isSubdomain) {
            return cleanHost.endsWith(".$cleanTarget")
        }
        return false
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
        public fun exact(domain: String): DomainMatcher = DomainMatcher(domain, isSubdomain = false)

        public fun sub(domain: String): DomainMatcher = DomainMatcher(domain, isSubdomain = true)

        public fun auto(domain: String): DomainMatcher {
            val trimmed = domain.trim()
            return if (trimmed.startsWith("*.")) {
                DomainMatcher(trimmed.substring(2), isSubdomain = true)
            } else if (trimmed.startsWith(".")) {
                DomainMatcher(trimmed.substring(1), isSubdomain = true)
            } else {
                DomainMatcher(trimmed, isSubdomain = false)
            }
        }
    }
}
