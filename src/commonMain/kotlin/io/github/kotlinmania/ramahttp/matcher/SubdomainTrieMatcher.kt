// port-lint: source matcher/subdomain_trie.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramacore.Extensions
import io.github.kotlinmania.ramacore.matcher.Matcher
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramanet.address.Domain
import io.github.kotlinmania.ramanet.address.DomainTrie

public class SubdomainTrieMatcher(
    domains: List<String>,
) : Matcher<Request> {
    private val trie: DomainTrie<Unit> = DomainTrie.new()

    init {
        for (domain in domains) {
            val clean = domain.trim().lowercase().removePrefix(".")
            if (Domain.isValidName(clean)) {
                trie.insert(Domain.parse(clean), Unit)
            }
        }
    }

    public fun isMatch(domain: Domain): Boolean = trie.isMatchParent(domain)

    public fun isMatch(domain: String): Boolean {
        val clean = domain.trim().lowercase().removePrefix(".")
        if (!Domain.isValidName(clean)) {
            return false
        }
        return trie.isMatchParent(Domain.parse(clean))
    }

    override fun matches(ext: Extensions?, input: Request): Boolean {
        ext?.hashCode()
        val host =
            input.uri.host
                ?: input.headers
                    .get(HeaderName.HOST)
                    ?.toStrOrNull()
                    ?.substringBefore(':')
                ?: return false
        return isMatch(host)
    }

    public companion object {
        public fun new(domains: List<String>): SubdomainTrieMatcher = SubdomainTrieMatcher(domains)

        public fun of(vararg domains: String): SubdomainTrieMatcher =
            SubdomainTrieMatcher(domains.toList())
    }
}
