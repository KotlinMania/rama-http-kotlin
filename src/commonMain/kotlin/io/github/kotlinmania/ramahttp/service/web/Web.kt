// port-lint: source service/web/mod.rs
package io.github.kotlinmania.ramahttp.service.web

import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.core.Service
import io.github.kotlinmania.ramahttp.matcher.HttpMatcher
import io.github.kotlinmania.ramahttp.service.web.endpoint.response.intoResponse
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import io.github.kotlinmania.ramahttp.types.StatusCode

public class Router : HttpService {
    private val routes: MutableList<Pair<HttpMatcher, Service<Request, Response>>> = mutableListOf()
    private var notFoundHandler: Service<Request, Response> =
        Service { _ -> StatusCode.NOT_FOUND.intoResponse() }

    public fun withRoute(matcher: HttpMatcher, service: Service<Request, Response>): Router {
        routes.add(Pair(matcher, service))
        return this
    }

    public fun withGet(path: String, service: Service<Request, Response>): Router =
        withRoute(HttpMatcher.methodGet().andPath(path), service)

    public fun withPost(path: String, service: Service<Request, Response>): Router =
        withRoute(HttpMatcher.methodPost().andPath(path), service)

    public fun withPut(path: String, service: Service<Request, Response>): Router =
        withRoute(HttpMatcher.methodPut().andPath(path), service)

    public fun withDelete(path: String, service: Service<Request, Response>): Router =
        withRoute(HttpMatcher.methodDelete().andPath(path), service)

    public fun withPatch(path: String, service: Service<Request, Response>): Router =
        withRoute(HttpMatcher.methodPatch().andPath(path), service)

    public fun withNotFound(service: Service<Request, Response>): Router {
        this.notFoundHandler = service
        return this
    }

    override suspend fun serve(req: Request): Response {
        for ((matcher, service) in routes) {
            if (matcher.matches(req.extensions, req)) {
                return service.serve(req)
            }
        }
        return notFoundHandler.serve(req)
    }

    public companion object {
        public fun new(): Router = Router()
    }
}

public class WebService : HttpService {
    private val endpoints: MutableList<Pair<HttpMatcher, Service<Request, Response>>> = mutableListOf()
    private var notFoundHandler: Service<Request, Response> =
        Service { _ -> StatusCode.NOT_FOUND.intoResponse() }

    public fun withMatcher(matcher: HttpMatcher, service: Service<Request, Response>): WebService {
        endpoints.add(Pair(matcher, service))
        return this
    }

    public fun withGet(path: String, service: Service<Request, Response>): WebService =
        withMatcher(HttpMatcher.methodGet().andPath(path), service)

    public fun withPost(path: String, service: Service<Request, Response>): WebService =
        withMatcher(HttpMatcher.methodPost().andPath(path), service)

    public fun withNotFound(service: Service<Request, Response>): WebService {
        this.notFoundHandler = service
        return this
    }

    override suspend fun serve(req: Request): Response {
        for ((matcher, service) in endpoints) {
            if (matcher.matches(req.extensions, req)) {
                return service.serve(req)
            }
        }
        return notFoundHandler.serve(req)
    }

    public companion object {
        public fun new(): WebService = WebService()
    }
}
