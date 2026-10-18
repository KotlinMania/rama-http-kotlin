// port-lint: tests layer/required_header/request.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.RAMA_ID_HEADER_VALUE
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.http.StatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class RequiredHeaderTest {
    @Test
    fun testAddRequiredRequestHeaders() =
        runTest {
            val layer = AddRequiredRequestHeadersLayer.new()
            val svc =
                layer.layer(
                    HttpService { req ->
                        assertTrue(req.headers.containsKey(HeaderName.HOST))
                        assertTrue(req.headers.containsKey(HeaderName.USER_AGENT))
                        Response.builder().status(StatusCode.OK).body(Body.empty())
                    },
                )

            val req = Request.builder().uri("http://www.example.com/").body(Body.empty())
            val resp = svc.serve(req)

            assertFalse(resp.headers.containsKey(HeaderName.HOST))
            assertFalse(resp.headers.containsKey(HeaderName.USER_AGENT))
        }

    @Test
    fun testAddRequiredRequestHeadersCustomUa() =
        runTest {
            val layer =
                AddRequiredRequestHeadersLayer
                    .new()
                    .withUserAgentHeaderValue(HeaderValue.fromStatic("foo"))
            val svc =
                layer.layer(
                    HttpService { req ->
                        assertTrue(req.headers.containsKey(HeaderName.HOST))
                        assertEquals("foo", req.headers.get(HeaderName.USER_AGENT)?.toStr())
                        Response.builder().status(StatusCode.OK).body(Body.empty())
                    },
                )

            val req = Request.builder().uri("http://www.example.com/").body(Body.empty())
            val resp = svc.serve(req)

            assertFalse(resp.headers.containsKey(HeaderName.HOST))
            assertFalse(resp.headers.containsKey(HeaderName.USER_AGENT))
        }

    @Test
    fun testAddRequiredRequestHeadersOverwrite() =
        runTest {
            val layer = AddRequiredRequestHeadersLayer.new().withOverwrite(true)
            val svc =
                layer.layer(
                    HttpService { req ->
                        assertEquals("127.0.0.1", req.headers.get(HeaderName.HOST)?.toStr())
                        assertEquals(RAMA_ID_HEADER_VALUE.toStr(), req.headers.get(HeaderName.USER_AGENT)?.toStr())
                        Response.builder().status(StatusCode.OK).body(Body.empty())
                    },
                )

            val req =
                Request
                    .builder()
                    .uri("http://127.0.0.1/")
                    .header(HeaderName.HOST, HeaderValue.fromString("example.com"))
                    .header(HeaderName.USER_AGENT, HeaderValue.fromString("test"))
                    .body(Body.empty())

            val resp = svc.serve(req)

            assertFalse(resp.headers.containsKey(HeaderName.HOST))
            assertFalse(resp.headers.containsKey(HeaderName.USER_AGENT))
        }

    @Test
    fun testAddRequiredRequestHeadersOverwriteCustomUa() =
        runTest {
            val layer =
                AddRequiredRequestHeadersLayer
                    .new()
                    .withOverwrite(true)
                    .withUserAgentHeaderValue(HeaderValue.fromStatic("foo"))
            val svc =
                layer.layer(
                    HttpService { req ->
                        assertEquals("127.0.0.1", req.headers.get(HeaderName.HOST)?.toStr())
                        assertEquals("foo", req.headers.get(HeaderName.USER_AGENT)?.toStr())
                        Response.builder().status(StatusCode.OK).body(Body.empty())
                    },
                )

            val req =
                Request
                    .builder()
                    .uri("http://127.0.0.1/")
                    .header(HeaderName.HOST, HeaderValue.fromString("example.com"))
                    .header(HeaderName.USER_AGENT, HeaderValue.fromString("test"))
                    .body(Body.empty())

            val resp = svc.serve(req)

            assertFalse(resp.headers.containsKey(HeaderName.HOST))
            assertFalse(resp.headers.containsKey(HeaderName.USER_AGENT))
        }

    @Test
    fun testAddRequiredResponseHeaders() =
        runTest {
            val layer = AddRequiredResponseHeadersLayer.new()
            val svc =
                layer.layer(
                    HttpService { req ->
                        assertFalse(req.headers.containsKey(HeaderName.SERVER))
                        assertFalse(req.headers.containsKey(HeaderName.DATE))
                        Response.builder().status(StatusCode.OK).body(Body.empty())
                    },
                )

            val req = Request.builder().body(Body.empty())
            val resp = svc.serve(req)

            assertEquals(RAMA_ID_HEADER_VALUE.toStr(), resp.headers.get(HeaderName.SERVER)?.toStr())
            assertTrue(resp.headers.containsKey(HeaderName.DATE))
        }

    @Test
    fun testAddRequiredResponseHeadersCustomServer() =
        runTest {
            val layer =
                AddRequiredResponseHeadersLayer
                    .new()
                    .withServerHeaderValue(HeaderValue.fromStatic("foo"))
            val svc =
                layer.layer(
                    HttpService { req ->
                        assertFalse(req.headers.containsKey(HeaderName.SERVER))
                        assertFalse(req.headers.containsKey(HeaderName.DATE))
                        Response.builder().status(StatusCode.OK).body(Body.empty())
                    },
                )

            val req = Request.builder().body(Body.empty())
            val resp = svc.serve(req)

            assertEquals("foo", resp.headers.get(HeaderName.SERVER)?.toStr())
            assertTrue(resp.headers.containsKey(HeaderName.DATE))
        }

    @Test
    fun testAddRequiredResponseHeadersOverwrite() =
        runTest {
            val layer = AddRequiredResponseHeadersLayer.new().withOverwrite(true)
            val svc =
                layer.layer(
                    HttpService { _ ->
                        val resp = Response.builder().status(StatusCode.OK).body(Body.empty())
                        resp.headers.insert(HeaderName.SERVER, HeaderValue.fromStatic("bar"))
                        resp.headers.insert(HeaderName.DATE, HeaderValue.fromStatic("bar"))
                        resp
                    },
                )

            val req = Request.builder().body(Body.empty())
            val resp = svc.serve(req)

            assertEquals(RAMA_ID_HEADER_VALUE.toStr(), resp.headers.get(HeaderName.SERVER)?.toStr())
            assertNotEquals("bar", resp.headers.get(HeaderName.DATE)?.toStr())
        }

    @Test
    fun testAddRequiredResponseHeadersOverwriteCustomServer() =
        runTest {
            val layer =
                AddRequiredResponseHeadersLayer
                    .new()
                    .withOverwrite(true)
                    .withServerHeaderValue(HeaderValue.fromStatic("foo"))
            val svc =
                layer.layer(
                    HttpService { _ ->
                        val resp = Response.builder().status(StatusCode.OK).body(Body.empty())
                        resp.headers.insert(HeaderName.SERVER, HeaderValue.fromStatic("bar"))
                        resp.headers.insert(HeaderName.DATE, HeaderValue.fromStatic("bar"))
                        resp
                    },
                )

            val req = Request.builder().body(Body.empty())
            val resp = svc.serve(req)

            assertEquals("foo", resp.headers.get(HeaderName.SERVER)?.toStr())
            assertNotEquals("bar", resp.headers.get(HeaderName.DATE)?.toStr())
        }

    @Test
    fun testFormatHttpDateRfcSample() {
        val rfcDate = formatHttpDate(784111777L)
        assertEquals("Sun, 06 Nov 1994 08:49:37 GMT", rfcDate)
    }
}
