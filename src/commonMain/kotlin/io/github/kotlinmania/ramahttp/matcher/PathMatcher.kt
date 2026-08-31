// port-lint: source rama-http/src/matcher/path/mod.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.Request

/**
 * Parameters that are inserted into [Extensions] when [PathMatcher] matches a request.
 */
public class UriParams(
    params: Map<String, String> = emptyMap(),
    public var glob: String? = null,
) {
    private val paramsMap: MutableMap<String, String> = params.toMutableMap()
    public val params: Map<String, String> get() = paramsMap

    public fun get(name: String): String? = paramsMap[name.lowercase()]

    public fun insert(name: String, value: String) {
        paramsMap[name.lowercase()] = value
    }

    public fun appendGlob(value: String) {
        glob = if (glob != null) "$glob/$value" else "/$value"
    }

    public fun iter(): List<Pair<String, String>> = paramsMap.map { Pair(it.key, it.value) }

    public fun clone(): UriParams = UriParams(paramsMap, glob)
}

public sealed class PathFragment {
    public class Literal(
        public val literal: String,
    ) : PathFragment()

    public class Param(
        public val name: String,
    ) : PathFragment()

    public object Glob : PathFragment()
}

public class PathMatcher private constructor(
    private val kind: Kind,
) : Matcher<Request> {
    private sealed class Kind {
        class Prefix(
            val prefix: String,
        ) : Kind()

        class Literal(
            val literal: String,
        ) : Kind()

        class FragmentList(
            val fragments: List<PathFragment>,
        ) : Kind()
    }

    public fun matchesPath(ext: Extensions?, rawPath: String): Boolean {
        val path = rawPath.trim().trim('/')
        when (val k = kind) {
            is Kind.Prefix -> {
                val cleanPrefix = k.prefix.trim().trim('/')
                if (cleanPrefix.isEmpty() || path.startsWith(cleanPrefix, ignoreCase = true)) {
                    return true
                }
                return false
            }
            is Kind.Literal -> {
                val cleanLiteral = k.literal.trim().trim('/')
                return path.equals(cleanLiteral, ignoreCase = true)
            }
            is Kind.FragmentList -> {
                val segments = if (path.isEmpty()) emptyList() else path.split('/').filter { it.isNotEmpty() }
                val fragments = k.fragments
                val params = UriParams()

                var segIdx = 0
                var fragIdx = 0

                while (segIdx < segments.size && fragIdx < fragments.size) {
                    val seg = segments[segIdx]
                    val frag = fragments[fragIdx]

                    when (frag) {
                        is PathFragment.Literal -> {
                            if (!seg.equals(frag.literal, ignoreCase = true)) {
                                return false
                            }
                            segIdx++
                            fragIdx++
                        }
                        is PathFragment.Param -> {
                            params.insert(frag.name, seg)
                            segIdx++
                            fragIdx++
                        }
                        is PathFragment.Glob -> {
                            params.appendGlob(seg)
                            segIdx++
                            // Glob consumes all remaining segments
                            while (segIdx < segments.size) {
                                params.appendGlob(segments[segIdx])
                                segIdx++
                            }
                            fragIdx++
                            break
                        }
                    }
                }

                if (fragIdx < fragments.size && fragments[fragIdx] is PathFragment.Glob && segIdx == segments.size) {
                    params.appendGlob("")
                    fragIdx++
                }

                if (fragIdx != fragments.size || segIdx != segments.size) {
                    return false
                }

                if (ext != null) {
                    ext.insert(params)
                }
                return true
            }
        }
    }

    override fun matches(ext: Extensions?, req: Request): Boolean = matchesPath(ext, req.uri.path)

    public companion object {
        public fun new(path: String): PathMatcher {
            val trimmed = path.trim().trim('/')
            if (!trimmed.contains('*') && !trimmed.contains('{') && !trimmed.contains('}') && !trimmed.contains(':')) {
                return PathMatcher(Kind.Literal(trimmed))
            }

            val parts = trimmed.split('/').filter { it.isNotEmpty() }
            val fragments = mutableListOf<PathFragment>()

            for ((index, part) in parts.withIndex()) {
                if (part.startsWith(':')) {
                    fragments.add(PathFragment.Param(part.removePrefix(":").lowercase()))
                } else if (part.startsWith('{') && part.endsWith('}') && part.length > 2) {
                    fragments.add(PathFragment.Param(part.substring(1, part.length - 1).lowercase()))
                } else if (part == "*" && index == parts.size - 1) {
                    fragments.add(PathFragment.Glob)
                } else {
                    fragments.add(PathFragment.Literal(part.lowercase()))
                }
            }

            if (fragments.all { it is PathFragment.Literal }) {
                return PathMatcher(Kind.Literal(trimmed))
            }

            return PathMatcher(Kind.FragmentList(fragments))
        }

        public fun newPrefix(path: String): PathMatcher =
            PathMatcher(Kind.Prefix(path.trim().trim('/')))

        public fun newLiteral(path: String): PathMatcher =
            PathMatcher(Kind.Literal(path.trim().trim('/')))
    }
}
