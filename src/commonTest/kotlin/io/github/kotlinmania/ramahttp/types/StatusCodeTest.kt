// port-lint: tests rama-http/src/types/status.rs
package io.github.kotlinmania.ramahttp.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StatusCodeTest {
    @Test
    fun testStatusCategories() {
        assertTrue(StatusCode.CONTINUE.isInformational())
        assertTrue(StatusCode.OK.isSuccess())
        assertTrue(StatusCode.MOVED_PERMANENTLY.isRedirection())
        assertTrue(StatusCode.NOT_FOUND.isClientError())
        assertTrue(StatusCode.INTERNAL_SERVER_ERROR.isServerError())

        assertFalse(StatusCode.OK.isClientError())
        assertFalse(StatusCode.NOT_FOUND.isServerError())
    }

    @Test
    fun testReasons() {
        assertEquals("OK", StatusCode.OK.canonicalReason())
        assertEquals("Not Found", StatusCode.NOT_FOUND.canonicalReason())
        assertEquals("Internal Server Error", StatusCode.INTERNAL_SERVER_ERROR.canonicalReason())
    }
}
