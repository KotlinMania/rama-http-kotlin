// port-lint: tests rama-http/src/types/method.rs
package io.github.kotlinmania.ramahttp.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MethodTest {
    @Test
    fun testStandardMethods() {
        assertEquals("GET", Method.GET.asStr())
        assertEquals("POST", Method.POST.asStr())
        assertEquals("PUT", Method.PUT.asStr())
        assertEquals("DELETE", Method.DELETE.asStr())
        assertEquals("HEAD", Method.HEAD.asStr())
        assertEquals("OPTIONS", Method.OPTIONS.asStr())
        assertEquals("CONNECT", Method.CONNECT.asStr())
        assertEquals("PATCH", Method.PATCH.asStr())
        assertEquals("TRACE", Method.TRACE.asStr())
    }

    @Test
    fun testCustomMethod() {
        val custom = Method.fromString("PURGE")
        assertEquals("PURGE", custom.asStr())
        assertEquals(custom, Method.fromString("PURGE"))
        assertNotEquals(custom, Method.GET)
    }

    @Test
    fun testFromBytes() {
        val method = Method.fromBytes("GET".encodeToByteArray())
        assertEquals(Method.GET, method)
    }
}
