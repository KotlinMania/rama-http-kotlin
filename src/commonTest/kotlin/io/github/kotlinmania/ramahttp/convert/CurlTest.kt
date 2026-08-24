// port-lint: tests convert/curl.rs
package io.github.kotlinmania.ramahttp.convert

import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import kotlin.test.Test
import kotlin.test.assertTrue

class CurlTest {

    @Test
    fun testCurlGet() {
        val req = Request.builder()
            .method(Method.GET)
            .uri("https://httpbin.org/get")
            .header(HeaderName.ACCEPT, HeaderValue.fromString("application/json"))
            .body(Body.empty())

        val cmd = cmdStringForRequest(req)
        assertTrue(cmd.contains("curl"))
        assertTrue(cmd.contains("-H 'accept: application/json'"))
        assertTrue(cmd.contains("'https://httpbin.org/get'"))
    }

    @Test
    fun testCurlPostData() {
        val payload = "hello=world".encodeToByteArray()
        val req = Request.builder()
            .method(Method.POST)
            .uri("https://httpbin.org/post")
            .body(Body.fromBytes(payload))

        val cmd = cmdStringForRequest(req, payload)
        assertTrue(cmd.contains("curl"))
        assertTrue(cmd.contains("--data-raw 'hello=world'"))
        assertTrue(cmd.contains("'https://httpbin.org/post'"))
    }
}
