// port-lint: tests rama-http/src/types/uri.rs
package io.github.kotlinmania.ramahttp.types

import kotlin.test.Test
import kotlin.test.assertEquals

class UriTest {
    @Test
    fun testParseFullUri() {
        val uri = Uri.fromString("https://example.com:8080/path/to/resource?query=1")
        assertEquals("https", uri.scheme?.asStr)
        assertEquals("example.com:8080", uri.authority?.asStr)
        assertEquals("example.com", uri.host)
        assertEquals(8080, uri.authority?.portU16)
        assertEquals("/path/to/resource", uri.path)
        assertEquals("query=1", uri.query)
    }

    @Test
    fun testParsePathOnly() {
        val uri = Uri.fromString("/index.html")
        assertEquals("/index.html", uri.path)
    }
}
