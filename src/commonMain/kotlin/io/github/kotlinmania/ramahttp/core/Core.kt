// port-lint: source lib.rs
package io.github.kotlinmania.ramahttp.core

import kotlin.reflect.KClass

/**
 * A type-safe heterogeneous map for request and connection metadata.
 */
public class Extensions {
    private val map: MutableMap<KClass<*>, Any> = mutableMapOf()

    public fun <T : Any> insert(kClass: KClass<T>, value: T): T? {
        @Suppress("UNCHECKED_CAST")
        return map.put(kClass, value) as? T
    }

    public inline fun <reified T : Any> insert(value: T): T? = insert(T::class, value)

    public fun <T : Any> get(kClass: KClass<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return map[kClass] as? T
    }

    public inline fun <reified T : Any> get(): T? = get(T::class)

    public fun <T : Any> remove(kClass: KClass<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return map.remove(kClass) as? T
    }

    public inline fun <reified T : Any> remove(): T? = remove(T::class)

    public fun <T : Any> contains(kClass: KClass<T>): Boolean = map.containsKey(kClass)

    public inline fun <reified T : Any> contains(): Boolean = contains(T::class)

    public fun clear() {
        map.clear()
    }

    public fun clone(): Extensions {
        val ext = Extensions()
        ext.map.putAll(this.map)
        return ext
    }
}

/**
 * Request processing context holding extensions.
 */
public class Context(
    public val extensions: Extensions = Extensions(),
) {
    public fun clone(): Context = Context(extensions.clone())
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
 * A layer that wraps an [HttpService] with middleware functionality.
 */
public fun interface HttpLayer : Layer<HttpService, HttpService> {
    override fun layer(inner: HttpService): HttpService
}

/**
 * A layer that wraps a [Service] with middleware functionality.
 */
public fun interface Layer<in S, out Target> {
    public fun layer(inner: S): Target
}

/**
 * A matcher that determines whether a request matches certain criteria.
 */
public fun interface Matcher<in Req> {
    public fun matches(ext: Extensions?, req: Req): Boolean
}

/**
 * A matcher that determines whether an HTTP request matches certain criteria.
 */
public fun interface RequestMatcher : Matcher<io.github.kotlinmania.ramahttp.types.Request> {
    override fun matches(ext: Extensions?, req: io.github.kotlinmania.ramahttp.types.Request): Boolean
}
