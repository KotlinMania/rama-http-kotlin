// port-lint: tests rama-http/src/io/mod.rs
package io.github.kotlinmania.ramahttp.io

import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IoTest {
    @Test
    fun testWriteHttpRequestGet() =
        runTest {
            val req =
                Request
                    .builder()
                    .method(Method.GET)
                    .uri("http://example.com")
                    .body(Body.empty())

            val bytes = writeHttpRequest(req, writeHeaders = true, writeBody = true)
            val text = bytes.decodeToString()
            assertEquals("GET / HTTP/1.1\r\n\r\n", text)
        }

    @Test
    fun testWriteHttpRequestGetWithHeaders() =
        runTest {
            val req =
                Request
                    .builder()
                    .method(Method.GET)
                    .uri("http://example.com")
                    .header(HeaderName.CONTENT_TYPE, HeaderValue.fromString("text/plain"))
                    .header(HeaderName.USER_AGENT, HeaderValue.fromString("test/0"))
                    .body(Body.empty())

            val bytes = writeHttpRequest(req, writeHeaders = true, writeBody = true)
            val text = bytes.decodeToString()
            assertEquals("GET / HTTP/1.1\r\ncontent-type: text/plain\r\nuser-agent: test/0\r\n\r\n", text)
        }

    @Test
    fun testWriteHttpRequestPostWithBody() =
        runTest {
            val req =
                Request
                    .builder()
                    .method(Method.POST)
                    .uri("http://example.com")
                    .header(HeaderName.CONTENT_TYPE, HeaderValue.fromString("text/plain"))
                    .header(HeaderName.USER_AGENT, HeaderValue.fromString("test/0"))
                    .body(Body.fromString("hello"))

            val bytes = writeHttpRequest(req, writeHeaders = true, writeBody = true)
            val text = bytes.decodeToString()
            assertEquals("POST / HTTP/1.1\r\ncontent-type: text/plain\r\nuser-agent: test/0\r\n\r\nhello", text)
        }

    @Test
    fun testWriteResponseOk() =
        runTest {
            val res =
                Response
                    .builder()
                    .status(StatusCode.OK)
                    .body(Body.empty())

            val bytes = writeHttpResponse(res, writeHeaders = true, writeBody = true)
            val text = bytes.decodeToString()
            assertEquals("HTTP/1.1 200 OK\r\n\r\n", text)
        }

    @Test
    fun testWriteResponseRedirect() =
        runTest {
            val res =
                Response
                    .builder()
                    .status(StatusCode.MOVED_PERMANENTLY)
                    .header(HeaderName.LOCATION, HeaderValue.fromString("http://example.com"))
                    .header(HeaderName.SERVER, HeaderValue.fromString("test/0"))
                    .body(Body.empty())

            val bytes = writeHttpResponse(res, writeHeaders = true, writeBody = true)
            val text = bytes.decodeToString()
            assertEquals("HTTP/1.1 301 Moved Permanently\r\nlocation: http://example.com\r\nserver: test/0\r\n\r\n", text)
        }

    @Test
    fun testWriteResponseWithHeadersAndBody() =
        runTest {
            val res =
                Response
                    .builder()
                    .status(StatusCode.OK)
                    .header(HeaderName.CONTENT_TYPE, HeaderValue.fromString("text/plain"))
                    .header(HeaderName.SERVER, HeaderValue.fromString("test/0"))
                    .body(Body.fromString("hello"))

            val bytes = writeHttpResponse(res, writeHeaders = true, writeBody = true)
            val text = bytes.decodeToString()
            assertEquals("HTTP/1.1 200 OK\r\ncontent-type: text/plain\r\nserver: test/0\r\n\r\nhello", text)
        }
}
