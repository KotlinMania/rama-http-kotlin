// port-lint: tests rama-http/src/layer/set_status.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.intoResponse
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.StatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LayerTest {
    @Test
    fun testSetStatusLayer() =
        runTest {
            val baseService = HttpService { _ -> StatusCode.OK.intoResponse() }
            val layer = SetStatusLayer(StatusCode.ACCEPTED)
            val service = layer.layer(baseService)

            val req = Request.builder().body(Body.empty())
            val res = service.serve(req)
            assertEquals(StatusCode.ACCEPTED, res.status)
        }

    @Test
    fun testNormalizePathLayer() =
        runTest {
            var observedPath: String? = null
            val baseService =
                HttpService { req ->
                    observedPath = req.uri.path
                    StatusCode.OK.intoResponse()
                }
            val layer = NormalizePathLayer.trimTrailingSlash()
            val service = layer.layer(baseService)

            val req = Request.builder().uri("/users/profile/").body(Body.empty())
            service.serve(req)
            assertEquals("/users/profile", observedPath)
        }

    @Test
    fun testRequestIdLayer() =
        runTest {
            val baseService = HttpService { _ -> StatusCode.OK.intoResponse() }
            val setReqId = SetRequestIdLayer(makeRequestId = { RequestId.fromString("req-12345") })
            val propReqId = PropagateRequestIdLayer()

            val service = setReqId.layer(propReqId.layer(baseService))
            val req = Request.builder().body(Body.empty())
            val res = service.serve(req)

            assertEquals("req-12345", res.headers.get(HeaderName.X_REQUEST_ID)?.toStr())
        }

    @Test
    fun testSetAndRemoveHeaderLayer() =
        runTest {
            val baseService =
                HttpService { _ ->
                    val res = StatusCode.OK.intoResponse()
                    res.headers.append(HeaderName.SERVER, HeaderValue.fromString("rama/0.1"))
                    res
                }
            val removeServer = RemoveResponseHeaderLayer(HeaderName.SERVER)
            val setCustom =
                SetResponseHeaderLayer.overriding(
                    HeaderName.fromString("X-Custom"),
                    HeaderValue.fromString("Value123"),
                )

            val service = setCustom.layer(removeServer.layer(baseService))
            val req = Request.builder().body(Body.empty())
            val res = service.serve(req)

            assertNull(res.headers.get(HeaderName.SERVER))
            assertEquals("Value123", res.headers.get(HeaderName.fromString("X-Custom"))?.toStr())
        }

    @Test
    fun testCorsLayer() =
        runTest {
            val baseService = HttpService { _ -> StatusCode.OK.intoResponse() }
            val cors = CorsLayer.permissive()
            val service = cors.layer(baseService)

            // Preflight OPTIONS
            val preflightReq =
                Request
                    .builder()
                    .method(Method.OPTIONS)
                    .header(HeaderName.ACCESS_CONTROL_REQUEST_METHOD, HeaderValue.fromString("POST"))
                    .body(Body.empty())

            val preflightRes = service.serve(preflightReq)
            assertEquals(StatusCode.NO_CONTENT, preflightRes.status)
            assertEquals("*", preflightRes.headers.get(HeaderName.ACCESS_CONTROL_ALLOW_ORIGIN)?.toStr())

            // Actual Request
            val normalReq = Request.builder().method(Method.GET).body(Body.empty())
            val normalRes = service.serve(normalReq)
            assertEquals(StatusCode.OK, normalRes.status)
            assertEquals("*", normalRes.headers.get(HeaderName.ACCESS_CONTROL_ALLOW_ORIGIN)?.toStr())
        }

    @Test
    fun testAuthLayer() =
        runTest {
            val baseService = HttpService { _ -> StatusCode.OK.intoResponse() }
            val validateAuth = ValidateAuthorizationLayer("Bearer") { token -> token == "secret-token" }
            val service = validateAuth.layer(baseService)

            val unauthReq = Request.builder().body(Body.empty())
            val unauthRes = service.serve(unauthReq)
            assertEquals(StatusCode.UNAUTHORIZED, unauthRes.status)

            val authReq =
                Request
                    .builder()
                    .header(HeaderName.AUTHORIZATION, HeaderValue.fromString("Bearer secret-token"))
                    .body(Body.empty())
            val authRes = service.serve(authReq)
            assertEquals(StatusCode.OK, authRes.status)
        }

    @Test
    fun testClassifyAndRetry() =
        runTest {
            var attempts = 0
            val flakyService =
                HttpService { _ ->
                    attempts++
                    if (attempts < 3) {
                        StatusCode.INTERNAL_SERVER_ERROR.intoResponse()
                    } else {
                        StatusCode.OK.intoResponse()
                    }
                }

            val retryLayer = RetryLayer(maxRetries = 3)
            val service = retryLayer.layer(flakyService)

            val req = Request.builder().body(Body.empty())
            val res = service.serve(req)

            assertEquals(StatusCode.OK, res.status)
            assertEquals(3, attempts)
        }
}
