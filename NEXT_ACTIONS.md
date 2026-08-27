# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 39/205 (19.0%)
- **Function parity:** 82/1974 matched (target 360) — 4.2%
- **Class/type parity:** 26/646 matched (target 136) — 4.0%
- **Combined symbol parity:** 108/2620 matched (target 496) — 4.1%
- **Average inline-code cosine:** 0.16 (function body across 24 matched files)
- **Average documentation cosine:** 0.15 (doc text across 24 matched files)
- **Cheat-zeroed Files:** 15
- **Critical Issues:** 37 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. io.request
- **Similarity:** 0.09 (needs 76% improvement)
- **Dependencies:** 22
- **Priority Score:** 22040510.0
- **Functions:** 1/5 matched (target 1)
- **Missing functions:** `test_write_http_request_get`, `test_write_http_request_get_with_headers`, `test_write_http_request_get_with_headers_and_query`, `test_write_http_request_post_with_headers_and_body`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Symbol Deficit:** 4 (functions: 4, types: 0)
- **Missing Tests:** 4 of 4 `#[test]` functions have no Kotlin counterpart
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **har.service** (28 deps)
   - Path: `rama-http/src/layer/har/service.rs`
   - Essential for 28 other files

2. **response.into_response** (17 deps)
   - Path: `rama-http/src/service/web/endpoint/response/into_response.rs`
   - Essential for 17 other files

3. **body.bytes** (17 deps)
   - Path: `rama-http/src/service/web/endpoint/extract/body/bytes.rs`
   - Essential for 17 other files

4. **har.layer** (13 deps)
   - Path: `rama-http/src/layer/har/layer.rs`
   - Essential for 13 other files

5. **har.extensions** (10 deps)
   - Path: `rama-http/src/layer/har/extensions.rs`
   - Essential for 10 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. io.request

- **Target:** `io.Request`
- **Similarity:** 0.09
- **Dependents:** 22
- **Priority Score:** 22040510.0
- **Functions:** 1/5 matched (target 1)
- **Missing functions:** `test_write_http_request_get`, `test_write_http_request_get_with_headers`, `test_write_http_request_get_with_headers_and_query`, `test_write_http_request_post_with_headers_and_body`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/4 matched

### 2. io.response

- **Target:** `io.Response`
- **Similarity:** 0.12
- **Dependents:** 9
- **Priority Score:** 9030409.0
- **Functions:** 1/4 matched (target 1)
- **Missing functions:** `test_write_response_ok`, `test_write_response_redirect`, `test_write_response_with_headers_and_body`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/3 matched

### 3. trace.body

- **Target:** `types.Body [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 8
- **Priority Score:** 8050510.0
- **Functions:** 0/3 matched (target 29)
- **Missing functions:** `poll_frame`, `is_end_stream`, `size_hint`
- **Types:** 0/2 matched (target 6)
- **Missing types:** `Data`, `Error`
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/body.rs` vs expected `layer/trace/body.rs`
- **Provenance warning:** port-lint provenance header matched only by basename: `tests:rama-http/src/types/body.rs` vs expected `layer/trace/body.rs`
- **Proposed provenance header:** `// port-lint: source layer/trace/body.rs` (current: `// port-lint: source rama-http/src/types/body.rs`)
- **Proposed provenance header:** `// port-lint: tests layer/trace/body.rs` (current: `// port-lint: tests rama-http/src/types/body.rs`)
- **Lint issues:** 2

### 4. matcher.uri

- **Target:** `matcher.UriMatcher [PROVENANCE-FALLBACK]`
- **Similarity:** 0.11
- **Dependents:** 6
- **Priority Score:** 6101509.0
- **Functions:** 4/13 matched (target 7)
- **Missing functions:** `is_match_bytes`, `matches_uri`, `from`, `matchest_uri_regex_match`, `matchest_uri_wildcard_match`, `matchest_uri_regex_no_match`, `matchest_uri_wildcard_no_match`, `uri_matches_regex_req`, `uri_matches_wildcard_req`
- **Types:** 1/2 matched
- **Missing types:** `Engine`
- **Tests:** 0/6 matched
- **Provenance warning:** port-lint provenance header matched only by basename: `tests:rama-http/src/types/uri.rs` vs expected `matcher/uri.rs`
- **Proposed provenance header:** `// port-lint: tests matcher/uri.rs` (current: `// port-lint: tests rama-http/src/types/uri.rs`)
- **Lint issues:** 2

### 5. matcher.method

- **Target:** `matcher.MethodMatcher [PROVENANCE-FALLBACK]`
- **Similarity:** 0.32
- **Dependents:** 5
- **Priority Score:** 5071207.0
- **Functions:** 4/9 matched (target 11)
- **Missing functions:** `bits`, `method`, `fmt`, `try_from`, `from_http_method`
- **Types:** 1/3 matched (target 2)
- **Missing types:** `NoMatchingMethodMatcher`, `Error`
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only by basename: `tests:rama-http/src/types/method.rs` vs expected `matcher/method.rs`
- **Proposed provenance header:** `// port-lint: tests matcher/method.rs` (current: `// port-lint: tests rama-http/src/types/method.rs`)
- **Lint issues:** 2

### 6. utils.header_value

- **Target:** `utils.HeaderValue`
- **Similarity:** 0.79
- **Dependents:** 5
- **Priority Score:** 5010502.0
- **Functions:** 2/3 matched (target 14)
- **Missing functions:** `fmt`
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 7. matcher.version

- **Target:** `matcher.VersionMatcher [PROVENANCE-FALLBACK]`
- **Similarity:** 0.26
- **Dependents:** 2
- **Priority Score:** 2091407.4
- **Functions:** 4/11 matched (target 10)
- **Missing functions:** `bits`, `version`, `fmt`, `try_from`, `test_version_matcher`, `test_version_matcher_any`, `test_version_matcher_fail`
- **Types:** 1/3 matched (target 2)
- **Missing types:** `NoMatchingVersionMatcher`, `Error`
- **Tests:** 0/3 matched
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/version.rs` vs expected `matcher/version.rs`
- **Proposed provenance header:** `// port-lint: source matcher/version.rs` (current: `// port-lint: source rama-http/src/types/version.rs`)
- **Lint issues:** 2

### 8. matcher.domain

- **Target:** `matcher.DomainMatcher`
- **Similarity:** 0.66
- **Dependents:** 2
- **Priority Score:** 2000403.4
- **Functions:** 3/3 matched (target 5)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Lint issues:** 1

### 9. io.upgrade

- **Target:** `io.Upgrade`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1272710.0
- **Functions:** 0/20 matched (target 2)
- **Missing functions:** `handle_upgrade`, `pending`, `new`, `downcast`, `__type_id`, `__is`, `extensions`, `extensions_mut`, `poll_read`, `poll_write`, `poll_write_vectored`, `poll_flush`, `poll_shutdown`, `is_write_vectored`, `fmt`, `has_handled_upgrade`, `poll`, `fulfill`, `manual`, `upgraded_downcast`
- **Types:** 0/7 matched (target 1)
- **Missing types:** `Upgraded`, `OnUpgrade`, `Parts`, `Pending`, `Io`, `Output`, `HandleUpgrade`
- **Tests:** 0/1 matched

### 10. body.zip_bomb

- **Target:** `body.ZipBomb`
- **Similarity:** 0.10
- **Dependents:** 1
- **Priority Score:** 1131809.0
- **Functions:** 4/15 matched (target 11)
- **Missing functions:** `default`, `new`, `into_generate_body`, `into_generate_response`, `from`, `fmt`, `write_nested_zip_file`, `write_fake_binary_data`, `generate_recursive_base_zip`, `read`, `poll_next`
- **Types:** 1/3 matched
- **Missing types:** `ZeroReader`, `Item`

### 11. matcher.header

- **Target:** `matcher.HeaderMatcher`
- **Similarity:** 0.21
- **Dependents:** 1
- **Priority Score:** 1101407.9
- **Functions:** 3/12 matched (target 4)
- **Missing functions:** `is`, `test_header_matcher_exists`, `test_header_matcher_exists_no_match`, `test_header_matcher_is`, `test_header_matcher_is_no_match`, `test_header_matcher_contains`, `test_header_matcher_contains_no_match`, `test_header_matcher_contains_multiple`, `test_header_matcher_contains_multiple_no_match`
- **Types:** 1/2 matched (target 5)
- **Missing types:** `HeaderMatcherKind`
- **Tests:** 0/8 matched
- **Lint issues:** 1

### 12. layer.set_status

- **Target:** `layer.SetStatus`
- **Similarity:** 0.48
- **Dependents:** 1
- **Priority Score:** 1040905.2
- **Functions:** 3/4 matched
- **Missing functions:** `new`
- **Types:** 2/5 matched (target 2)
- **Missing types:** `Service`, `Output`, `Error`

### 13. matcher.mod

- **Target:** `matcher.HttpMatcher [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 669310.0
- **Functions:** 26/90 matched (target 39)
- **Missing functions:** `clone`, `fmt`, `or_method`, `and_method_delete`, `or_method_delete`, `and_method_get`, `or_method_get`, `and_method_head`, `or_method_head`, `and_method_options`, `or_method_options`, `and_method_patch`, `or_method_patch`, `and_method_post`, `or_method_post`, `and_method_put`, `or_method_put`, `and_method_trace`, `or_method_trace`, `and_method_connect`, `or_method_connect`, `subdomain`, `and_subdomain`, `or_domain`, `or_subdomain`, `and_version`, `or_version`, `uri_regex`, `uri_wildcard`, `and_uri_regex`, `and_uri_wildcard`, `or_uri_regex`, `or_uri_wildcard`, `path_literal`, `or_path`, `or_header`, `and_header_exists`, `or_header_exists`, `and_header_contains`, `or_header_contains`, `socket`, `and_socket`, `or_socket`, `get`, `and_custom`, `or_custom`, `any_subdomain`, `and_any_subdomain`, `or_any_subdomain`, `post`, `put`, `delete`, `patch`, `head`, `options`, `trace`, `connect`, `test_matcher_and_combination`, `test_matcher_negation_with_and_combination`, `test_matcher_and_combination_negated`, `test_matcher_ors_combination`, `test_matcher_negation_with_ors_combination`, `test_matcher_ors_combination_negated`, `test_matcher_or_and_or_and_negation`
- **Types:** 1/3 matched (target 13)
- **Missing types:** `HttpMatcherKind`, `BooleanMatcher`
- **Tests:** 0/7 matched

### 14. layer.normalize_path

- **Target:** `layer.NormalizePath`
- **Similarity:** 0.08
- **Dependents:** 0
- **Priority Score:** 263309.2
- **Functions:** 4/27 matched (target 4)
- **Missing functions:** `default`, `new`, `works`, `handle`, `is_noop_if_no_trailing_slash`, `maintains_query`, `removes_multiple_trailing_slashes`, `removes_multiple_trailing_slashes_even_with_query`, `is_noop_on_index`, `removes_multiple_trailing_slashes_on_index`, `removes_multiple_trailing_slashes_on_index_even_with_query`, `removes_multiple_preceding_slashes_even_with_query`, `removes_multiple_preceding_slashes`, `append_works`, `is_noop_if_trailing_slash`, `append_maintains_query`, `append_only_keeps_one_slash`, `append_only_keeps_one_slash_even_with_query`, `append_is_noop_on_index`, `append_removes_multiple_trailing_slashes_on_index`, `append_removes_multiple_trailing_slashes_on_index_even_with_query`, `append_removes_multiple_preceding_slashes_even_with_query`, `append_removes_multiple_preceding_slashes`
- **Types:** 3/6 matched (target 3)
- **Missing types:** `Service`, `Output`, `Error`
- **Tests:** 0/21 matched

### 15. path.mod

- **Target:** `matcher.PathMatcher [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 223410.0
- **Functions:** 9/26 matched (target 10)
- **Missing functions:** `glob`, `deserialize`, `extend`, `from_iter`, `body_text`, `status`, `fmt`, `into_response`, `fragment_count`, `try_remove_literal_prefix`, `matches_path_inner`, `test_path_matcher_match_path`, `some`, `none`, `test_path_matcher_match_path_literal`, `test_path_matcher_match_path_prefix`, `test_deserialize_uri_params`
- **Types:** 3/8 matched (target 9)
- **Missing types:** `UriParamsDeserializeError`, `PathMatcherKind`, `PathMatch`, `TestCase`, `Person`
- **Tests:** 0/6 matched

### 16. layer.request_id

- **Target:** `layer.RequestId`
- **Similarity:** 0.09
- **Dependents:** 0
- **Priority Score:** 222909.1
- **Functions:** 2/17 matched (target 6)
- **Missing functions:** `new`, `header_value`, `into_header_value`, `from`, `request_id`, `x_request_id`, `into_layer`, `make_request_id`, `make_nano_id`, `basic`, `basic_with_request_id`, `other_middleware_setting_request_id_on_response`, `handler`, `uuid`, `nanoid`
- **Types:** 5/12 matched (target 5)
- **Missing types:** `MakeRequestId`, `Service`, `Output`, `Error`, `MakeRequestUuid`, `MakeRequestNanoid`, `Counter`
- **Tests:** 0/6 matched

### 17. convert.curl

- **Target:** `convert.Curl`
- **Similarity:** 0.02
- **Dependents:** 0
- **Priority Score:** 181909.8
- **Functions:** 1/17 matched (target 4)
- **Missing functions:** `cmd_string_for_request_parts_and_payload`, `cmd_for_request_parts`, `cmd_for_request_parts_and_payload`, `write_uri`, `write_single`, `write_tuple`, `write_header`, `write_curl_command_for_request_parts`, `test_cmd_string_for_request_parts_from_har`, `test_cmd_string_for_request_with_http_proxy_no_auth`, `test_cmd_string_for_request_with_ipv4_preference`, `test_cmd_string_for_request_with_ipv6_preference`, `test_cmd_string_for_request_with_http_proxy_with_auth_basic_only_username`, `test_cmd_string_for_request_with_http_proxy_with_auth_basic`, `test_cmd_string_for_request_with_http_proxy_with_auth_bearer`, `test_cmd_string_for_request_with_socks5_proxy`
- **Types:** 0/2 matched (target 1)
- **Missing types:** `CurlCommandWriter`, `TestCase`
- **Tests:** 0/8 matched

### 18. retry.mod

- **Target:** `layer.Retry [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 161710.0
- **Functions:** 1/11 matched (target 2)
- **Missing functions:** `new`, `fmt`, `source`, `test_service_with_managed_retry`, `retry`, `request`, `extensions`, `do_not_retry_extensions`, `assert_serve_ok`, `assert_serve_err`
- **Types:** 0/6 matched (target 2)
- **Missing types:** `Retry`, `RetryError`, `RetryErrorKind`, `Output`, `Error`, `State`
- **Tests:** 0/7 matched

### 19. response.header

- **Target:** `types.Header [PROVENANCE-FALLBACK]`
- **Similarity:** 0.01
- **Dependents:** 0
- **Priority Score:** 161709.9
- **Functions:** 1/7 matched (target 38)
- **Missing functions:** `make_header_value`, `make_header_value_maker`, `new`, `fmt`, `call`, `apply`
- **Types:** 0/10 matched (target 4)
- **Missing types:** `MakeHeaderValueFactory`, `MakeHeaderValue`, `Maker`, `MakeHeaderValueDefault`, `TypedHeaderAsMaker`, `MakeHeaderValueFactoryFn`, `BoxMakeHeaderValueFactoryFn`, `MakeHeaderValueFn`, `BoxMakeHeaderValueFn`, `InsertHeaderMode`
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/header.rs` vs expected `layer/set_header/response/header.rs`
- **Provenance warning:** port-lint provenance header matched only by basename: `tests:rama-http/src/types/header.rs` vs expected `layer/set_header/response/header.rs`
- **Proposed provenance header:** `// port-lint: source layer/set_header/response/header.rs` (current: `// port-lint: source rama-http/src/types/header.rs`)
- **Proposed provenance header:** `// port-lint: tests layer/set_header/response/header.rs` (current: `// port-lint: tests rama-http/src/types/header.rs`)
- **Lint issues:** 2

### 20. remove_header.response

- **Target:** `types.Response [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 161610.0
- **Functions:** 0/10 matched (target 14)
- **Missing functions:** `prefix`, `exact`, `hop_by_hop`, `layer`, `into_layer`, `serve`, `remove_response_header_prefix`, `remove_response_header_exact`, `remove_response_header_hop_by_hop`, `remove_response_header_hop_by_hop_with_headers_in_connect`
- **Types:** 0/6 matched (target 3)
- **Missing types:** `RemoveResponseHeaderLayer`, `RemoveResponseHeaderMode`, `Service`, `RemoveResponseHeader`, `Output`, `Error`
- **Tests:** 0/4 matched
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/response.rs` vs expected `layer/remove_header/response.rs`
- **Proposed provenance header:** `// port-lint: source layer/remove_header/response.rs` (current: `// port-lint: source rama-http/src/types/response.rs`)
- **Lint issues:** 1

### 21. remove_header.request

- **Target:** `types.Request [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 161610.0
- **Functions:** 0/10 matched (target 15)
- **Missing functions:** `prefix`, `exact`, `hop_by_hop`, `layer`, `into_layer`, `serve`, `remove_request_header_prefix`, `remove_request_header_exact`, `remove_request_header_hop_by_hop`, `remove_request_header_hop_by_hop_with_connection_list`
- **Types:** 0/6 matched (target 3)
- **Missing types:** `RemoveRequestHeaderLayer`, `RemoveRequestHeaderMode`, `Service`, `RemoveRequestHeader`, `Output`, `Error`
- **Tests:** 0/4 matched
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/request.rs` vs expected `layer/remove_header/request.rs`
- **Proposed provenance header:** `// port-lint: source layer/remove_header/request.rs` (current: `// port-lint: source rama-http/src/types/request.rs`)
- **Lint issues:** 1

### 22. classify.mod

- **Target:** `layer.Classify [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 151710.0
- **Functions:** 0/7 matched (target 3)
- **Missing functions:** `new`, `make_classifier`, `map_failure_class`, `classify_eos`, `classify_error`, `fmt`, `classify_response`
- **Types:** 2/10 matched (target 4)
- **Missing types:** `MakeClassifier`, `SharedClassifier`, `FailureClass`, `ClassifyEos`, `Classifier`, `ClassifyResponse`, `NeverClassifyEos`, `ServerErrorsFailureClass`

### 23. layer.sensitive_headers

- **Target:** `layer.SensitiveHeaders`
- **Similarity:** 0.05
- **Dependents:** 0
- **Priority Score:** 141609.5
- **Functions:** 2/7 matched (target 3)
- **Missing functions:** `new`, `from_shared`, `into_layer`, `multiple_value_header`, `response_set_cookie`
- **Types:** 0/9 matched (target 3)
- **Missing types:** `SetSensitiveHeadersLayer`, `Service`, `SetSensitiveHeaders`, `SetSensitiveRequestHeadersLayer`, `SetSensitiveRequestHeaders`, `Output`, `Error`, `SetSensitiveResponseHeadersLayer`, `SetSensitiveResponseHeaders`
- **Tests:** 0/2 matched

### 24. response.into_response_parts

- **Target:** `response.IntoResponse`
- **Similarity:** 0.02
- **Dependents:** 0
- **Priority Score:** 141509.8
- **Functions:** 1/10 matched (target 4)
- **Missing functions:** `into_response_parts`, `headers`, `headers_mut`, `extensions`, `extensions_mut`, `fmt`, `key`, `value`, `source`
- **Types:** 0/5 matched (target 3)
- **Missing types:** `IntoResponseParts`, `Error`, `ResponseParts`, `TryIntoHeaderError`, `TryIntoHeaderErrorKind`

### 25. cors.mod

- **Target:** `layer.Cors [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101410.0
- **Functions:** 3/9 matched (target 14)
- **Missing functions:** `new`, `very_permissive`, `is_allow_credentials_any`, `handle_options_request`, `default`, `into_layer`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Service`, `Cors`, `Output`, `Error`

### 26. matcher.subdomain_trie

- **Target:** `matcher.SubdomainTrieMatcher`
- **Similarity:** 0.13
- **Dependents:** 0
- **Priority Score:** 50808.7
- **Functions:** 2/7 matched (target 4)
- **Missing functions:** `new`, `from_iter`, `test_trie_matching`, `test_path_matching_with_trie`, `test_non_matching_path`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/3 matched
- **Lint issues:** 1

### 27. remove_header.mod

- **Target:** `layer.RemoveHeader [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 40410.0
- **Functions:** 0/4 matched
- **Missing functions:** `remove_headers_by_prefix`, `remove_headers_by_exact_name`, `remove_hop_by_hop_request_headers`, `remove_hop_by_hop_response_headers`
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 28. extract.uri

- **Target:** `types.Uri [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/1 matched (target 17)
- **Missing functions:** `from_request`
- **Types:** 0/1 matched (target 5)
- **Missing types:** `Rejection`
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/uri.rs` vs expected `service/web/endpoint/extract/uri.rs`
- **Proposed provenance header:** `// port-lint: source service/web/endpoint/extract/uri.rs` (current: `// port-lint: source rama-http/src/types/uri.rs`)
- **Lint issues:** 1

### 29. extract.method

- **Target:** `types.Method [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/1 matched (target 6)
- **Missing functions:** `from_parts_state_ref_pair`
- **Types:** 0/1 matched
- **Missing types:** `Rejection`
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/types/method.rs` vs expected `service/web/endpoint/extract/method.rs`
- **Proposed provenance header:** `// port-lint: source service/web/endpoint/extract/method.rs` (current: `// port-lint: source rama-http/src/types/method.rs`)
- **Lint issues:** 1

### 30. utils.request

- **Target:** `utils.Request`
- **Similarity:** 0.34
- **Dependents:** 0
- **Priority Score:** 10206.6
- **Functions:** 1/2 matched (target 1)
- **Missing functions:** `test_request_uri`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 31. set_header.mod

- **Target:** `layer.SetHeader [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 8)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 32. web.mod

- **Target:** `web.Web [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 17)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 33. convert.mod

- **Target:** `convert.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 34. auth.mod

- **Target:** `layer.Auth [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 35. redirect.mod

- **Target:** `redirect.Redirect [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 36. util.mod

- **Target:** `core.Core [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 11)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only by basename: `rama-http/src/core/mod.rs` vs expected `layer/util/mod.rs`
- **Proposed provenance header:** `// port-lint: source layer/util/mod.rs` (current: `// port-lint: source rama-http/src/core/mod.rs`)
- **Lint issues:** 1

### 37. body.mod

- **Target:** `body.Mod [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 11)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only by basename: `tests:rama-http/src/layer/mod.rs` vs expected `body/mod.rs`
- **Provenance warning:** port-lint provenance header matched only by basename: `tests:rama-http/src/service/mod.rs` vs expected `body/mod.rs`
- **Proposed provenance header:** `// port-lint: tests body/mod.rs` (current: `// port-lint: tests rama-http/src/layer/mod.rs`)
- **Proposed provenance header:** `// port-lint: tests body/mod.rs` (current: `// port-lint: tests rama-http/src/service/mod.rs`)
- **Lint issues:** 2

### 38. io.mod

- **Target:** `io.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 39. rama-http.lib

- **Target:** `ramahttp.Lib [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 21)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

