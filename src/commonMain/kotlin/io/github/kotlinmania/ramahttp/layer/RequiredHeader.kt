// port-lint: source layer/required_header/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.core.HttpLayer
import io.github.kotlinmania.ramahttp.core.HttpService
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.RAMA_ID_HEADER_VALUE
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Response
import kotlin.time.Clock

internal fun formatHttpDate(epochSeconds: Long): String {
    val days = (epochSeconds / 86400).toInt()
    val remSec = (epochSeconds % 86400).toInt().let { if (it < 0) it + 86400 else it }
    val hour = remSec / 3600
    val minute = (remSec % 3600) / 60
    val second = remSec % 60

    val dayOfWeekNum = ((days + 4) % 7 + 7) % 7
    val dayNames = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val dayOfWeek = dayNames[dayOfWeekNum]

    val z = days + 719468
    val era = (if (z >= 0) z else z - 146096) / 146097
    val doe = z - era * 146097
    val yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365
    var y = yoe + era * 400
    val doy = doe - (365 * yoe + yoe / 4 - yoe / 100)
    val mp = (5 * doy + 2) / 153
    val d = doy - (153 * mp + 2) / 5 + 1
    val m = mp + (if (mp < 10) 3 else -9)
    if (m <= 2) y += 1

    val monthNames = arrayOf("", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val month = monthNames[m]

    val dayStr = d.toString().padStart(2, '0')
    val yearStr = y.toString().padStart(4, '0')
    val hourStr = hour.toString().padStart(2, '0')
    val minStr = minute.toString().padStart(2, '0')
    val secStr = second.toString().padStart(2, '0')

    return "$dayOfWeek, $dayStr $month $yearStr $hourStr:$minStr:$secStr GMT"
}

public class AddRequiredRequestHeadersLayer(
    public val overwrite: Boolean = false,
    public val userAgentHeaderValue: HeaderValue? = null,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService =
        AddRequiredRequestHeaders(inner, overwrite, userAgentHeaderValue)

    public fun withOverwrite(overwrite: Boolean): AddRequiredRequestHeadersLayer =
        AddRequiredRequestHeadersLayer(overwrite = overwrite, userAgentHeaderValue = userAgentHeaderValue)

    public fun withUserAgentHeaderValue(value: HeaderValue?): AddRequiredRequestHeadersLayer =
        AddRequiredRequestHeadersLayer(overwrite = overwrite, userAgentHeaderValue = value)

    public companion object {
        public fun new(): AddRequiredRequestHeadersLayer = AddRequiredRequestHeadersLayer()
    }
}

public class AddRequiredRequestHeaders(
    public val inner: HttpService,
    public val overwrite: Boolean = false,
    public val userAgentHeaderValue: HeaderValue? = null,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        if (overwrite || !req.headers.containsKey(HeaderName.HOST)) {
            val authority = req.uri.authority
            if (authority != null) {
                val hostValue =
                    if (authority.portU16 != null && authority.portU16 != 80 && authority.portU16 != 443) {
                        authority.asStr
                    } else {
                        authority.host
                    }
                req.headers.insert(HeaderName.HOST, HeaderValue.fromString(hostValue))
            }
        }

        val defaultUserAgent = userAgentHeaderValue ?: RAMA_ID_HEADER_VALUE
        if (overwrite) {
            req.headers.insert(HeaderName.USER_AGENT, defaultUserAgent)
        } else if (!req.headers.containsKey(HeaderName.USER_AGENT)) {
            req.headers.insert(HeaderName.USER_AGENT, defaultUserAgent)
        }

        return inner.serve(req)
    }

    public fun withOverwrite(overwrite: Boolean): AddRequiredRequestHeaders =
        AddRequiredRequestHeaders(inner, overwrite = overwrite, userAgentHeaderValue = userAgentHeaderValue)

    public fun withUserAgentHeaderValue(value: HeaderValue?): AddRequiredRequestHeaders =
        AddRequiredRequestHeaders(inner, overwrite = overwrite, userAgentHeaderValue = value)
}

public class AddRequiredResponseHeadersLayer(
    public val overwrite: Boolean = false,
    public val serverHeaderValue: HeaderValue? = null,
) : HttpLayer {
    override fun layer(inner: HttpService): HttpService =
        AddRequiredResponseHeaders(inner, overwrite, serverHeaderValue)

    public fun withOverwrite(overwrite: Boolean): AddRequiredResponseHeadersLayer =
        AddRequiredResponseHeadersLayer(overwrite = overwrite, serverHeaderValue = serverHeaderValue)

    public fun withServerHeaderValue(value: HeaderValue?): AddRequiredResponseHeadersLayer =
        AddRequiredResponseHeadersLayer(overwrite = overwrite, serverHeaderValue = value)

    public companion object {
        public fun new(): AddRequiredResponseHeadersLayer = AddRequiredResponseHeadersLayer()
    }
}

public class AddRequiredResponseHeaders(
    public val inner: HttpService,
    public val overwrite: Boolean = false,
    public val serverHeaderValue: HeaderValue? = null,
) : HttpService {
    override suspend fun serve(req: Request): Response {
        val resp = inner.serve(req)

        val defaultServer = serverHeaderValue ?: RAMA_ID_HEADER_VALUE
        if (overwrite) {
            resp.headers.insert(HeaderName.SERVER, defaultServer)
        } else if (!resp.headers.containsKey(HeaderName.SERVER)) {
            resp.headers.insert(HeaderName.SERVER, defaultServer)
        }

        if (overwrite || !resp.headers.containsKey(HeaderName.DATE)) {
            val dateStr = formatHttpDate(Clock.System.now().epochSeconds)
            resp.headers.insert(HeaderName.DATE, HeaderValue.fromString(dateStr))
        }

        return resp
    }

    public fun withOverwrite(overwrite: Boolean): AddRequiredResponseHeaders =
        AddRequiredResponseHeaders(inner, overwrite = overwrite, serverHeaderValue = serverHeaderValue)

    public fun withServerHeaderValue(value: HeaderValue?): AddRequiredResponseHeaders =
        AddRequiredResponseHeaders(inner, overwrite = overwrite, serverHeaderValue = value)
}
