// port-lint: source rama-http/src/lib.rs
package io.github.kotlinmania.ramahttp

public typealias Method = io.github.kotlinmania.ramahttp.types.Method
public typealias StatusCode = io.github.kotlinmania.ramahttp.types.StatusCode
public typealias Version = io.github.kotlinmania.ramahttp.types.Version
public typealias Uri = io.github.kotlinmania.ramahttp.types.Uri
public typealias HeaderName = io.github.kotlinmania.ramahttp.types.HeaderName
public typealias HeaderValue = io.github.kotlinmania.ramahttp.types.HeaderValue
public typealias HeaderMap = io.github.kotlinmania.ramahttp.types.HeaderMap
public typealias Body = io.github.kotlinmania.ramahttp.types.Body
public typealias Request = io.github.kotlinmania.ramahttp.types.Request
public typealias Response = io.github.kotlinmania.ramahttp.types.Response
public typealias RequestBuilder = io.github.kotlinmania.ramahttp.types.RequestBuilder
public typealias ResponseBuilder = io.github.kotlinmania.ramahttp.types.ResponseBuilder
public typealias HttpError = io.github.kotlinmania.ramahttp.types.HttpError

public typealias Extensions = io.github.kotlinmania.ramahttp.core.Extensions
public typealias Context = io.github.kotlinmania.ramahttp.core.Context
public typealias Service<Req, Res> = io.github.kotlinmania.ramahttp.core.Service<Req, Res>
public typealias HttpService = io.github.kotlinmania.ramahttp.core.HttpService
public typealias Layer<S, Target> = io.github.kotlinmania.ramahttp.core.Layer<S, Target>
public typealias HttpLayer = io.github.kotlinmania.ramahttp.core.HttpLayer
public typealias Matcher<Req> = io.github.kotlinmania.ramahttp.core.Matcher<Req>
public typealias RequestMatcher = io.github.kotlinmania.ramahttp.core.RequestMatcher
