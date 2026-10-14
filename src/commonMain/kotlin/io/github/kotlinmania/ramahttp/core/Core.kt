// port-lint: source lib.rs
package io.github.kotlinmania.ramahttp.core

import io.github.kotlinmania.ramacore.Extensions as RamaExtensions
import io.github.kotlinmania.ramacore.layer.Layer as RamaLayer
import io.github.kotlinmania.ramacore.matcher.Matcher as RamaMatcher

public typealias Extensions = RamaExtensions

public fun Extensions.clone(): Extensions = copy()

/**
 * Request processing context holding extensions.
 */
public class Context(
    public val extensions: Extensions = Extensions(),
) {
    public fun clone(): Context = Context(extensions.copy())
}

/**
 * An asynchronous service handling requests of type [Req] and producing responses of type [Res].
 */
public fun interface Service<in Req, out Res> {
    public suspend fun serve(req: Req): Res
}

/**
 * An asynchronous HTTP service handling [io.github.kotlinmania.ramahttp.types.Request] and producing [io.github.kotlinmania.ramahttp.types.Response].
 */
public fun interface HttpService : Service<io.github.kotlinmania.ramahttp.types.Request, io.github.kotlinmania.ramahttp.types.Response> {
    override suspend fun serve(req: io.github.kotlinmania.ramahttp.types.Request): io.github.kotlinmania.ramahttp.types.Response
}

/**
 * A layer that wraps a [Service] with middleware functionality.
 */
public typealias Layer<S, Target> = RamaLayer<S, Target>

/**
 * A layer that wraps an [HttpService] with middleware functionality.
 */
public fun interface HttpLayer : Layer<HttpService, HttpService> {
    override fun layer(inner: HttpService): HttpService
}

/**
 * A matcher that determines whether a request matches certain criteria.
 */
public typealias Matcher<Req> = RamaMatcher<Req>

/**
 * A matcher that determines whether an HTTP request matches certain criteria.
 */
public fun interface RequestMatcher : Matcher<io.github.kotlinmania.ramahttp.types.Request> {
    override fun matches(ext: Extensions?, req: io.github.kotlinmania.ramahttp.types.Request): Boolean
}
