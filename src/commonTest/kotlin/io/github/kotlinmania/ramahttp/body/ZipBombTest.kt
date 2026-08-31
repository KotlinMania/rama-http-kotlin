// port-lint: tests rama-http/src/body/zip_bomb.rs
package io.github.kotlinmania.ramahttp.body

import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.StatusCode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ZipBombTest {
    @Test
    fun testZipBombHeaders() {
        val bomb = ZipBomb(filename = "test_trap")
        val headers: Map<HeaderName, HeaderValue> = bomb.generateResponseHeaders()

        assertEquals("application/zip", headers[HeaderName.CONTENT_TYPE]?.toStr())
        assertEquals("attachment; filename=test_trap.zip", headers[HeaderName.CONTENT_DISPOSITION]?.toStr())
        assertEquals("none", headers[HeaderName.fromString("Robots")]?.toStr())
    }

    @Test
    fun testZipBombResponse() {
        val bomb = ZipBomb()
        val res = bomb.generateResponse()
        assertEquals(StatusCode.OK, res.status)
        assertTrue(res.headers.containsKey(HeaderName.CONTENT_TYPE))
    }
}
