// port-lint: tests service/mod.rs
package io.github.kotlinmania.ramahttp.service

import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.service.redirect.RedirectHttpToHttps
import io.github.kotlinmania.ramahttp.service.redirect.RedirectStatic
import io.github.kotlinmania.ramahttp.service.web.Router
import io.github.kotlinmania.ramahttp.service.web.WebService
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.Html
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.Json
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ServiceTest {

    @Test
    fun testRedirectHttpToHttps() = runTest {
        val redirect = RedirectHttpToHttps().overwritePort(8443)
        val req = Request.builder()
            .uri("http://example.com/login?ref=1")
            .body(Body.empty())

        val res = redirect.serve(req)
        assertEquals(StatusCode.PERMANENT_REDIRECT, res.status)
        assertEquals("https://example.com:8443/login?ref=1", res.headers.get(HeaderName.LOCATION)?.toStr())
    }

    @Test
    fun testRedirectStatic() = runTest {
        val redirect = RedirectStatic.permanent("/new-home")
        val req = Request.builder().uri("/old-home").body(Body.empty())

        val res = redirect.serve(req)
        assertEquals(StatusCode.PERMANENT_REDIRECT, res.status)
        assertEquals("/new-home", res.headers.get(HeaderName.LOCATION)?.toStr())
    }

    @Test
    fun testRouter() = runTest {
        val router = Router.new()
            .withGet("/hello", Service { _ -> Html("<h1>Hello</h1>").intoResponse() })
            .withPost("/api/data", Service { _ -> Json("{\"status\":\"ok\"}").intoResponse() })

        val req1 = Request.builder().method(Method.GET).uri("/hello").body(Body.empty())
        val res1 = router.serve(req1)
        assertEquals(StatusCode.OK, res1.status)
        assertEquals("text/html; charset=utf-8", res1.headers.get(HeaderName.CONTENT_TYPE)?.toStr())
        assertEquals("<h1>Hello</h1>", res1.body.collectUtf8())

        val req2 = Request.builder().method(Method.GET).uri("/notfound").body(Body.empty())
        val res2 = router.serve(req2)
        assertEquals(StatusCode.NOT_FOUND, res2.status)
    }

    @Test
    fun testWebService() = runTest {
        val web = WebService.new()
            .withGet("/status", Service { _ -> Json("{\"alive\":true}").intoResponse() })

        val req = Request.builder().method(Method.GET).uri("/status").body(Body.empty())
        val res = web.serve(req)
        assertEquals(StatusCode.OK, res.status)
        assertEquals("{\"alive\":true}", res.body.collectUtf8())
    }
}
