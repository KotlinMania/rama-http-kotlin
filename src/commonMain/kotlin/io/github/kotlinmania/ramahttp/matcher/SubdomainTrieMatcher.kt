// port-lint: source rama-http/src/matcher/subdomain_trie.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request

public class SubdomainTrieMatcher(
    domains: List<String>,
) : Matcher<Request> {
    private class Node {
        var isTerminal: Boolean = false
        val children: MutableMap<String, Node> = mutableMapOf()
    }

    private val root = Node()

    init {
        for (domain in domains) {
            insert(domain)
        }
    }

    private fun insert(domain: String) {
        val clean = domain.trim().lowercase().removePrefix(".")
        val labels = clean.split('.').reversed()
        var curr = root
        for (label in labels) {
            curr = curr.children.getOrPut(label) { Node() }
        }
        curr.isTerminal = true
    }

    public fun isMatch(domain: String): Boolean {
        val clean = domain.trim().lowercase().removePrefix(".")
        val labels = clean.split('.').reversed()
        var curr = root
        for (label in labels) {
            val next = curr.children[label] ?: return false
            if (next.isTerminal) {
                return true
            }
            curr = next
        }
        return curr.isTerminal
    }

    override fun matches(ext: Extensions?, req: Request): Boolean {
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
        public fun of(vararg domains: String): SubdomainTrieMatcher =
            SubdomainTrieMatcher(domains.toList())
    }
}
