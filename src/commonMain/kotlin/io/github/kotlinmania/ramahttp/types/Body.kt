// port-lint: source types/body.rs
package io.github.kotlinmania.ramahttp.types

import kotlinx.coroutines.flow.Flow

/**
 * Represents the body payload of an HTTP request or response.
 */
public sealed class Body {
    public abstract suspend fun collect(): ByteArray

    public suspend fun collectUtf8(): String = collect().decodeToString()

    public abstract fun isStreaming(): Boolean

    public open fun isEmpty(): Boolean =
        when (this) {
            is Empty -> true
            is Bytes -> bytes.isEmpty()
            is Text -> text.isEmpty()
            is Stream -> false
        }

    public open fun intoBytes(): ByteArray =
        when (this) {
            is Empty -> ByteArray(0)
            is Bytes -> bytes.copyOf()
            is Text -> text.encodeToByteArray()
            is Stream -> ByteArray(0)
        }

    public open fun intoString(): String =
        when (this) {
            is Empty -> ""
            is Bytes -> bytes.decodeToString()
            is Text -> text
            is Stream -> ""
        }

    public class Empty : Body() {
        override suspend fun collect(): ByteArray = ByteArray(0)

        override fun isStreaming(): Boolean = false

        override fun toString(): String = "Body.Empty"

        override fun equals(other: Any?): Boolean = other is Empty

        override fun hashCode(): Int = 0
    }

    public class Bytes(
        public val bytes: ByteArray,
    ) : Body() {
        override suspend fun collect(): ByteArray = bytes.copyOf()

        override fun isStreaming(): Boolean = false

        override fun toString(): String = "Body.Bytes(size=${bytes.size})"

        override fun equals(other: Any?): Boolean = other is Bytes && bytes.contentEquals(other.bytes)

        override fun hashCode(): Int = bytes.contentHashCode()
    }

    public class Text(
        public val text: String,
    ) : Body() {
        override suspend fun collect(): ByteArray = text.encodeToByteArray()

        override fun isStreaming(): Boolean = false

        override fun toString(): String = "Body.Text(len=${text.length})"

        override fun equals(other: Any?): Boolean = other is Text && text == other.text

        override fun hashCode(): Int = text.hashCode()
    }

    public class Stream(
        public val flow: Flow<ByteArray>,
    ) : Body() {
        override suspend fun collect(): ByteArray {
            val chunks = mutableListOf<ByteArray>()
            var totalSize = 0
            flow.collect { chunk ->
                chunks.add(chunk)
                totalSize += chunk.size
            }
            val result = ByteArray(totalSize)
            var offset = 0
            for (chunk in chunks) {
                chunk.copyInto(result, destinationOffset = offset)
                offset += chunk.size
            }
            return result
        }

        override fun isStreaming(): Boolean = true

        override fun toString(): String = "Body.Stream"
    }

    public companion object {
        public fun empty(): Body = Empty()

        public fun fromBytes(bytes: ByteArray): Body = Bytes(bytes)

        public fun fromString(str: String): Body = Text(str)

        public fun fromStream(flow: Flow<ByteArray>): Body = Stream(flow)
    }
}
