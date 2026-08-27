// port-lint: source rama-http/src/matcher/version.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.core.Matcher
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Version

/**
 * A matcher that matches one or more HTTP versions.
 */
public class VersionMatcher private constructor(
    public val bits: UShort,
) : Matcher<Request> {
    public fun or(other: VersionMatcher): VersionMatcher = VersionMatcher((this.bits or other.bits))

    public fun and(other: VersionMatcher): VersionMatcher = VersionMatcher((this.bits and other.bits))

    public fun contains(other: VersionMatcher): Boolean = (this.bits and other.bits) == other.bits

    override fun matches(ext: Extensions?, req: Request): Boolean {
        val vm = fromVersion(req.version) ?: return false
        return contains(vm)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersionMatcher) return false
        return bits == other.bits
    }

    override fun hashCode(): Int = bits.hashCode()

    public companion object {
        public val HTTP_09: VersionMatcher = VersionMatcher((1 shl 1).toUShort())
        public val HTTP_10: VersionMatcher = VersionMatcher((1 shl 2).toUShort())
        public val HTTP_11: VersionMatcher = VersionMatcher((1 shl 3).toUShort())
        public val HTTP_2: VersionMatcher = VersionMatcher((1 shl 4).toUShort())
        public val HTTP_3: VersionMatcher = VersionMatcher((1 shl 5).toUShort())

        public fun fromBits(bits: UShort): VersionMatcher = VersionMatcher(bits)

        public fun fromVersion(version: Version): VersionMatcher? =
            when (version) {
                Version.HTTP_09 -> HTTP_09
                Version.HTTP_10 -> HTTP_10
                Version.HTTP_11 -> HTTP_11
                Version.HTTP_2 -> HTTP_2
                Version.HTTP_3 -> HTTP_3
            }
    }
}
