// port-lint: source rama-http/src/matcher/uri.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.utils.requestUri

public fun interface UriPredicate {
    public fun matches(uri: String): Boolean
}

public class UriMatcher private constructor(
    private val predicate: UriPredicate,
) : Matcher<Request> {
    public fun isMatch(uriStr: String): Boolean = predicate.matches(uriStr)

    override fun matches(ext: Extensions?, req: Request): Boolean {
        ext?.hashCode()
        val uri = requestUri(req)
        return predicate.matches(uri.toString())
    }

    public companion object {
        public fun custom(predicate: UriPredicate): UriMatcher = UriMatcher(predicate)

        public fun regex(pattern: String): UriMatcher {
            val r = Regex(pattern)
            return UriMatcher { r.containsMatchIn(it) }
        }

        public fun wildcard(pattern: String): UriMatcher {
            val regexPattern =
                buildString {
                    append("^")
                    for (char in pattern) {
                        when (char) {
                            '*' -> append(".*")
                            '?' -> append(".")
                            '.', '\\', '+', '(', ')', '[', ']', '{', '}', '^', '$', '|' -> {
                                append('\\').append(char)
                            }
                            else -> append(char)
                        }
                    }
                    append("$")
                }
            val r = Regex(regexPattern, RegexOption.IGNORE_CASE)
            return UriMatcher { r.matches(it) }
        }
    }
}
