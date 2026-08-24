// port-lint: source io/upgrade.rs
package io.github.kotlinmania.ramahttp.io

import io.github.kotlinmania.ramahttp.HeaderName
import io.github.kotlinmania.ramahttp.Request
import io.github.kotlinmania.ramahttp.Response
import io.github.kotlinmania.ramahttp.StatusCode

/**
 * HTTP upgrade helpers (for WebSockets, HTTP/2 cleartext, etc.).
 */
public object Upgrade {

    public fun isUpgradeRequest(req: Request): Boolean {
        val connection = req.headers.get(HeaderName.CONNECTION)?.toStr() ?: return false
        val upgrade = req.headers.get(HeaderName.UPGRADE)?.toStr() ?: return false
        return connection.contains("upgrade", ignoreCase = true) && upgrade.isNotEmpty()
    }

    public fun isUpgradeResponse(res: Response): Boolean {
        return res.status == StatusCode.SWITCHING_PROTOCOLS
    }
}
