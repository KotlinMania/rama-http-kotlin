// port-lint: tests types/header.rs
package io.github.kotlinmania.ramahttp.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HeaderTest {
    @Test
    fun testHeaderCaseInsensitivity() {
        val h1 = HeaderName.fromString("Content-Type")
        val h2 = HeaderName.fromString("content-type")
        val h3 = HeaderName.fromString("CONTENT-TYPE")

        assertEquals(h1, h2)
        assertEquals(h2, h3)
        assertEquals(h1.hashCode(), h2.hashCode())
    }

    @Test
    fun testHeaderMapOperations() {
        val map = HeaderMap()
        map.insert(HeaderName.CONTENT_TYPE, HeaderValue.fromString("application/json"))
        map.append(HeaderName.ACCEPT, HeaderValue.fromString("text/html"))
        map.append(HeaderName.ACCEPT, HeaderValue.fromString("application/xhtml+xml"))

        assertEquals("application/json", map.get(HeaderName.CONTENT_TYPE)?.toStr())
        assertEquals(2, map.getAll(HeaderName.ACCEPT).size)
        assertTrue(map.containsKey(HeaderName.CONTENT_TYPE))
        assertFalse(map.containsKey(HeaderName.AUTHORIZATION))

        map.remove(HeaderName.CONTENT_TYPE)
        assertNull(map.get(HeaderName.CONTENT_TYPE))
    }
}
