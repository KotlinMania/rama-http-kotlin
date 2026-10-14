// port-lint: source io/upgrade.rs
package io.github.kotlinmania.ramahttp.io

import io.github.kotlinmania.http.StatusCode
import io.github.kotlinmania.ramacore.Extensions
import io.github.kotlinmania.ramacore.ExtensionsMut
import io.github.kotlinmania.ramacore.ExtensionsRef
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import kotlinx.coroutines.CompletableDeferred

/**
 * An upgraded HTTP connection.
 */
public class Upgraded(
    stream: Any,
    public val readBuf: ByteArray = ByteArray(0),
    private val extensionsStore: Extensions = Extensions(),
) : ExtensionsMut {
    public val io: Any = stream

    public constructor(stream: Any, extensions: Extensions) : this(stream, ByteArray(0), extensions)

    override fun extensions(): Extensions = extensionsStore

    override fun extensionsMut(): Extensions = extensionsStore

    public inline fun <reified T : Any> downcast(): Result<Parts<T>> {
        val typed = io as? T ?: return Result.failure(IllegalStateException("Upgraded downcast failed"))
        return Result.success(Parts(typed, readBuf, extensions()))
    }

    override fun toString(): String = "Upgraded"

    public companion object {
        public fun new(stream: Any, readBuf: ByteArray = ByteArray(0)): Upgraded {
            val ext = (stream as? ExtensionsRef)?.extensions()?.copy() ?: Extensions()
            return Upgraded(stream, readBuf, ext)
        }
    }
}

/**
 * The deconstructed parts of an [Upgraded] connection.
 */
public class Parts<out T>(
    stream: T,
    public val readBuf: ByteArray,
    public val extensions: Extensions,
) {
    public val io: T = stream
}

/**
 * A future for a possible HTTP upgrade.
 */
public class OnUpgrade internal constructor(
    private val deferred: CompletableDeferred<Result<Upgraded>>,
) {
    public fun hasHandledUpgrade(): Boolean = deferred.isCompleted

    public suspend fun await(): Result<Upgraded> = deferred.await()
}

/**
 * A pending upgrade, created with [pending].
 */
public class Pending internal constructor(
    private val deferred: CompletableDeferred<Result<Upgraded>>,
) {
    public fun fulfill(upgraded: Upgraded) {
        deferred.complete(Result.success(upgraded))
    }

    public fun manual() {
        deferred.complete(Result.failure(IllegalStateException("OnUpgrade: manual upgrade failed")))
    }
}

/**
 * Initiate an upgrade.
 */
public fun pending(): Pair<Pending, OnUpgrade> {
    val deferred = CompletableDeferred<Result<Upgraded>>()
    return Pair(Pending(deferred), OnUpgrade(deferred))
}

/**
 * HTTP upgrade helpers (for WebSockets, HTTP/2 cleartext, etc.).
 */
public object Upgrade {
    public fun isUpgradeRequest(req: Request): Boolean {
        val connection = req.headers.get(HeaderName.CONNECTION)?.toStr() ?: return false
        val upgrade = req.headers.get(HeaderName.UPGRADE)?.toStr() ?: return false
        return connection.contains("upgrade", ignoreCase = true) && upgrade.isNotEmpty()
    }

    public fun isUpgradeResponse(res: Response): Boolean = res.status == StatusCode.SWITCHING_PROTOCOLS
}
