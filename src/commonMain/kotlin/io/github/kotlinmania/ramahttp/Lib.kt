// port-lint: source lib.rs
package io.github.kotlinmania.ramahttp

public typealias Method = io.github.kotlinmania.ramahttp.types.Method
public typealias Version = io.github.kotlinmania.ramahttp.types.Version
public typealias Uri = io.github.kotlinmania.ramahttp.types.Uri
public typealias Scheme = io.github.kotlinmania.ramahttp.types.Scheme
public typealias HeaderName = io.github.kotlinmania.ramahttp.types.HeaderName
public typealias HeaderValue = io.github.kotlinmania.ramahttp.types.HeaderValue
public typealias HeaderMap = io.github.kotlinmania.ramahttp.types.HeaderMap
public typealias Body = io.github.kotlinmania.ramahttp.types.Body
public typealias StreamingBody = io.github.kotlinmania.ramahttp.types.Body.Stream
public typealias Request = io.github.kotlinmania.ramahttp.types.Request
public typealias Response = io.github.kotlinmania.ramahttp.types.Response
public typealias RequestBuilder = io.github.kotlinmania.ramahttp.types.RequestBuilder
public typealias ResponseBuilder = io.github.kotlinmania.ramahttp.types.ResponseBuilder
public typealias HttpError = io.github.kotlinmania.ramahttp.types.HttpError

public typealias Context = io.github.kotlinmania.ramahttp.core.Context
public typealias HttpService = io.github.kotlinmania.ramahttp.core.HttpService
public typealias HttpLayer = io.github.kotlinmania.ramahttp.core.HttpLayer
public typealias RequestMatcher = io.github.kotlinmania.ramahttp.core.RequestMatcher

public val RAMA_ID_HEADER_VALUE: HeaderValue = io.github.kotlinmania.ramahttp.types.RAMA_ID_HEADER_VALUE
public typealias AddRequiredRequestHeadersLayer = io.github.kotlinmania.ramahttp.layer.AddRequiredRequestHeadersLayer
public typealias AddRequiredRequestHeaders = io.github.kotlinmania.ramahttp.layer.AddRequiredRequestHeaders
public typealias AddRequiredResponseHeadersLayer = io.github.kotlinmania.ramahttp.layer.AddRequiredResponseHeadersLayer
public typealias AddRequiredResponseHeaders = io.github.kotlinmania.ramahttp.layer.AddRequiredResponseHeaders
