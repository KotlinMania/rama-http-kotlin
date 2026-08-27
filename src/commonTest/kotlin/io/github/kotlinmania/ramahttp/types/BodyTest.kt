// port-lint: tests rama-http/src/types/body.rs
package io.github.kotlinmania.ramahttp.types

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BodyTest {
    @Test
    fun testEmptyBody() =
        runTest {
            val body = Body.empty()
            assertEquals("", body.collectUtf8())
        }

    @Test
    fun testTextBody() =
        runTest {
            val body = Body.fromString("hello world")
            assertEquals("hello world", body.collectUtf8())
        }

    @Test
    fun testStreamBody() =
        runTest {
            val flow = flowOf("hello ".encodeToByteArray(), "world".encodeToByteArray())
            val body = Body.fromStream(flow)
            assertEquals("hello world", body.collectUtf8())
        }
}
