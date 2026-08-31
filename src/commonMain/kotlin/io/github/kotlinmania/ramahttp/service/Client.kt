// port-lint: source rama-http/src/service/client.rs
package io.github.kotlinmania.ramahttp.service

import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response

public class HttpClient(
    private val transport: HttpService,
) {
    public suspend fun send(req: Request): Response = transport.serve(req)

    public suspend fun get(uri: String): Response {
        val req =
            Request
                .builder()
                .method(Method.GET)
                .uri(uri)
                .body(Body.empty())
        return send(req)
    }

    public suspend fun post(uri: String, body: Body): Response {
        val req =
            Request
                .builder()
                .method(Method.POST)
                .uri(uri)
                .body(body)
        return send(req)
    }

    public suspend fun put(uri: String, body: Body): Response {
        val req =
            Request
                .builder()
                .method(Method.PUT)
                .uri(uri)
                .body(body)
        return send(req)
    }

    public suspend fun delete(uri: String): Response {
        val req =
            Request
                .builder()
                .method(Method.DELETE)
                .uri(uri)
                .body(Body.empty())
        return send(req)
    }

    public companion object {
        public fun new(transport: HttpService): HttpClient = HttpClient(transport)

        public fun new(transport: Service<Request, Response>): HttpClient =
            HttpClient(HttpService { transport.serve(it) })
    }
}
