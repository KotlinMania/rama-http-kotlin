# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 29/205 (14.1%)
- **Function parity:** 78/1948 matched (target 220) — 4.0%
- **Class/type parity:** 26/618 matched (target 88) — 4.2%
- **Combined symbol parity:** 104/2566 matched (target 308) — 4.1%
- **Average inline-code cosine:** 0.22 (function body across 15 matched files)
- **Average documentation cosine:** 0.14 (doc text across 15 matched files)
- **Cheat-zeroed Files:** 13
- **Critical Issues:** 27 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **har.service** (28 deps)
   - Path: `rama-http/src/layer/har/service.rs`
   - Essential for 28 other files

2. **io.request** (22 deps)
   - Path: `rama-http/src/io/request.rs`
   - Essential for 22 other files

3. **response.into_response** (17 deps)
   - Path: `rama-http/src/service/web/endpoint/response/into_response.rs`
   - Essential for 17 other files

4. **body.bytes** (17 deps)
   - Path: `rama-http/src/service/web/endpoint/extract/body/bytes.rs`
   - Essential for 17 other files

5. **har.layer** (13 deps)
   - Path: `rama-http/src/layer/har/layer.rs`
   - Essential for 13 other files

6. **har.extensions** (10 deps)
   - Path: `rama-http/src/layer/har/extensions.rs`
   - Essential for 10 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. matcher.uri

- **Target:** `matcher.UriMatcher`
- **Similarity:** 0.11
- **Dependents:** 6
- **Priority Score:** 6101509.0
- **Functions:** 4/13 matched (target 5)
- **Missing functions:** `is_match_bytes`, `matches_uri`, `from`, `matchest_uri_regex_match`, `matchest_uri_wildcard_match`, `matchest_uri_regex_no_match`, `matchest_uri_wildcard_no_match`, `uri_matches_regex_req`, `uri_matches_wildcard_req`
- **Types:** 1/2 matched (target 1)
- **Missing types:** `Engine`
- **Tests:** 0/6 matched

### 2. matcher.method

- **Target:** `matcher.MethodMatcher`
- **Similarity:** 0.32
- **Dependents:** 5
- **Priority Score:** 5071207.0
- **Functions:** 4/9 matched (target 8)
- **Missing functions:** `bits`, `method`, `fmt`, `try_from`, `from_http_method`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `NoMatchingMethodMatcher`, `Error`
- **Tests:** 0/1 matched

### 3. utils.header_value

- **Target:** `utils.HeaderValue`
- **Similarity:** 0.79
- **Dependents:** 5
- **Priority Score:** 5010502.0
- **Functions:** 2/3 matched (target 14)
- **Missing functions:** `fmt`
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 4. matcher.version

- **Target:** `matcher.VersionMatcher`
- **Similarity:** 0.26
- **Dependents:** 2
- **Priority Score:** 2091407.4
- **Functions:** 4/11 matched (target 8)
- **Missing functions:** `bits`, `version`, `fmt`, `try_from`, `test_version_matcher`, `test_version_matcher_any`, `test_version_matcher_fail`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `NoMatchingVersionMatcher`, `Error`
- **Tests:** 0/3 matched

### 5. matcher.domain

- **Target:** `matcher.DomainMatcher`
- **Similarity:** 0.66
- **Dependents:** 2
- **Priority Score:** 2000403.4
- **Functions:** 3/3 matched (target 5)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 6. io.upgrade

- **Target:** `io.Upgrade`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1272710.0
- **Functions:** 0/20 matched (target 2)
- **Missing functions:** `handle_upgrade`, `pending`, `new`, `downcast`, `__type_id`, `__is`, `extensions`, `extensions_mut`, `poll_read`, `poll_write`, `poll_write_vectored`, `poll_flush`, `poll_shutdown`, `is_write_vectored`, `fmt`, `has_handled_upgrade`, `poll`, `fulfill`, `manual`, `upgraded_downcast`
- **Types:** 0/7 matched (target 1)
- **Missing types:** `Upgraded`, `OnUpgrade`, `Parts`, `Pending`, `Io`, `Output`, `HandleUpgrade`
- **Tests:** 0/1 matched

### 7. body.zip_bomb

- **Target:** `body.ZipBomb`
- **Similarity:** 0.10
- **Dependents:** 1
- **Priority Score:** 1131809.0
- **Functions:** 4/15 matched (target 11)
- **Missing functions:** `default`, `new`, `into_generate_body`, `into_generate_response`, `from`, `fmt`, `write_nested_zip_file`, `write_fake_binary_data`, `generate_recursive_base_zip`, `read`, `poll_next`
- **Types:** 1/3 matched
- **Missing types:** `ZeroReader`, `Item`

### 8. matcher.header

- **Target:** `matcher.HeaderMatcher`
- **Similarity:** 0.21
- **Dependents:** 1
- **Priority Score:** 1101407.9
- **Functions:** 3/12 matched (target 4)
- **Missing functions:** `is`, `test_header_matcher_exists`, `test_header_matcher_exists_no_match`, `test_header_matcher_is`, `test_header_matcher_is_no_match`, `test_header_matcher_contains`, `test_header_matcher_contains_no_match`, `test_header_matcher_contains_multiple`, `test_header_matcher_contains_multiple_no_match`
- **Types:** 1/2 matched (target 5)
- **Missing types:** `HeaderMatcherKind`
- **Tests:** 0/8 matched

### 9. layer.set_status

- **Target:** `layer.SetStatus`
- **Similarity:** 0.48
- **Dependents:** 1
- **Priority Score:** 1040905.2
- **Functions:** 3/4 matched (target 11)
- **Missing functions:** `new`
- **Types:** 2/5 matched (target 3)
- **Missing types:** `Service`, `Output`, `Error`

### 10. matcher.mod

- **Target:** `matcher.HttpMatcher [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 669310.0
- **Functions:** 26/90 matched (target 39)
- **Missing functions:** `clone`, `fmt`, `or_method`, `and_method_delete`, `or_method_delete`, `and_method_get`, `or_method_get`, `and_method_head`, `or_method_head`, `and_method_options`, `or_method_options`, `and_method_patch`, `or_method_patch`, `and_method_post`, `or_method_post`, `and_method_put`, `or_method_put`, `and_method_trace`, `or_method_trace`, `and_method_connect`, `or_method_connect`, `subdomain`, `and_subdomain`, `or_domain`, `or_subdomain`, `and_version`, `or_version`, `uri_regex`, `uri_wildcard`, `and_uri_regex`, `and_uri_wildcard`, `or_uri_regex`, `or_uri_wildcard`, `path_literal`, `or_path`, `or_header`, `and_header_exists`, `or_header_exists`, `and_header_contains`, `or_header_contains`, `socket`, `and_socket`, `or_socket`, `get`, `and_custom`, `or_custom`, `any_subdomain`, `and_any_subdomain`, `or_any_subdomain`, `post`, `put`, `delete`, `patch`, `head`, `options`, `trace`, `connect`, `test_matcher_and_combination`, `test_matcher_negation_with_and_combination`, `test_matcher_and_combination_negated`, `test_matcher_ors_combination`, `test_matcher_negation_with_ors_combination`, `test_matcher_ors_combination_negated`, `test_matcher_or_and_or_and_negation`
- **Types:** 1/3 matched (target 13)
- **Missing types:** `HttpMatcherKind`, `BooleanMatcher`
- **Tests:** 0/7 matched

### 11. layer.normalize_path

- **Target:** `layer.NormalizePath`
- **Similarity:** 0.08
- **Dependents:** 0
- **Priority Score:** 263309.2
- **Functions:** 4/27 matched (target 4)
- **Missing functions:** `default`, `new`, `works`, `handle`, `is_noop_if_no_trailing_slash`, `maintains_query`, `removes_multiple_trailing_slashes`, `removes_multiple_trailing_slashes_even_with_query`, `is_noop_on_index`, `removes_multiple_trailing_slashes_on_index`, `removes_multiple_trailing_slashes_on_index_even_with_query`, `removes_multiple_preceding_slashes_even_with_query`, `removes_multiple_preceding_slashes`, `append_works`, `is_noop_if_trailing_slash`, `append_maintains_query`, `append_only_keeps_one_slash`, `append_only_keeps_one_slash_even_with_query`, `append_is_noop_on_index`, `append_removes_multiple_trailing_slashes_on_index`, `append_removes_multiple_trailing_slashes_on_index_even_with_query`, `append_removes_multiple_preceding_slashes_even_with_query`, `append_removes_multiple_preceding_slashes`
- **Types:** 3/6 matched (target 3)
- **Missing types:** `Service`, `Output`, `Error`
- **Tests:** 0/21 matched

### 12. path.mod

- **Target:** `matcher.PathMatcher [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 223410.0
- **Functions:** 9/26 matched (target 10)
- **Missing functions:** `glob`, `deserialize`, `extend`, `from_iter`, `body_text`, `status`, `fmt`, `into_response`, `fragment_count`, `try_remove_literal_prefix`, `matches_path_inner`, `test_path_matcher_match_path`, `some`, `none`, `test_path_matcher_match_path_literal`, `test_path_matcher_match_path_prefix`, `test_deserialize_uri_params`
- **Types:** 3/8 matched (target 9)
- **Missing types:** `UriParamsDeserializeError`, `PathMatcherKind`, `PathMatch`, `TestCase`, `Person`
- **Tests:** 0/6 matched

### 13. layer.request_id

- **Target:** `layer.RequestId`
- **Similarity:** 0.09
- **Dependents:** 0
- **Priority Score:** 222909.1
- **Functions:** 2/17 matched (target 6)
- **Missing functions:** `new`, `header_value`, `into_header_value`, `from`, `request_id`, `x_request_id`, `into_layer`, `make_request_id`, `make_nano_id`, `basic`, `basic_with_request_id`, `other_middleware_setting_request_id_on_response`, `handler`, `uuid`, `nanoid`
- **Types:** 5/12 matched (target 5)
- **Missing types:** `MakeRequestId`, `Service`, `Output`, `Error`, `MakeRequestUuid`, `MakeRequestNanoid`, `Counter`
- **Tests:** 0/6 matched

### 14. convert.curl

- **Target:** `convert.Curl`
- **Similarity:** 0.02
- **Dependents:** 0
- **Priority Score:** 181909.8
- **Functions:** 1/17 matched (target 4)
- **Missing functions:** `cmd_string_for_request_parts_and_payload`, `cmd_for_request_parts`, `cmd_for_request_parts_and_payload`, `write_uri`, `write_single`, `write_tuple`, `write_header`, `write_curl_command_for_request_parts`, `test_cmd_string_for_request_parts_from_har`, `test_cmd_string_for_request_with_http_proxy_no_auth`, `test_cmd_string_for_request_with_ipv4_preference`, `test_cmd_string_for_request_with_ipv6_preference`, `test_cmd_string_for_request_with_http_proxy_with_auth_basic_only_username`, `test_cmd_string_for_request_with_http_proxy_with_auth_basic`, `test_cmd_string_for_request_with_http_proxy_with_auth_bearer`, `test_cmd_string_for_request_with_socks5_proxy`
- **Types:** 0/2 matched (target 1)
- **Missing types:** `CurlCommandWriter`, `TestCase`
- **Tests:** 0/8 matched

### 15. retry.mod

- **Target:** `layer.Retry [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 161710.0
- **Functions:** 1/11 matched (target 2)
- **Missing functions:** `new`, `fmt`, `source`, `test_service_with_managed_retry`, `retry`, `request`, `extensions`, `do_not_retry_extensions`, `assert_serve_ok`, `assert_serve_err`
- **Types:** 0/6 matched (target 2)
- **Missing types:** `Retry`, `RetryError`, `RetryErrorKind`, `Output`, `Error`, `State`
- **Tests:** 0/7 matched

### 16. classify.mod

- **Target:** `layer.Classify [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 151710.0
- **Functions:** 0/7 matched (target 3)
- **Missing functions:** `new`, `make_classifier`, `map_failure_class`, `classify_eos`, `classify_error`, `fmt`, `classify_response`
- **Types:** 2/10 matched (target 4)
- **Missing types:** `MakeClassifier`, `SharedClassifier`, `FailureClass`, `ClassifyEos`, `Classifier`, `ClassifyResponse`, `NeverClassifyEos`, `ServerErrorsFailureClass`

### 17. layer.sensitive_headers

- **Target:** `layer.SensitiveHeaders`
- **Similarity:** 0.05
- **Dependents:** 0
- **Priority Score:** 141609.5
- **Functions:** 2/7 matched (target 3)
- **Missing functions:** `new`, `from_shared`, `into_layer`, `multiple_value_header`, `response_set_cookie`
- **Types:** 0/9 matched (target 3)
- **Missing types:** `SetSensitiveHeadersLayer`, `Service`, `SetSensitiveHeaders`, `SetSensitiveRequestHeadersLayer`, `SetSensitiveRequestHeaders`, `Output`, `Error`, `SetSensitiveResponseHeadersLayer`, `SetSensitiveResponseHeaders`
- **Tests:** 0/2 matched

### 18. response.into_response_parts

- **Target:** `response.IntoResponse`
- **Similarity:** 0.02
- **Dependents:** 0
- **Priority Score:** 141509.8
- **Functions:** 1/10 matched (target 4)
- **Missing functions:** `into_response_parts`, `headers`, `headers_mut`, `extensions`, `extensions_mut`, `fmt`, `key`, `value`, `source`
- **Types:** 0/5 matched (target 3)
- **Missing types:** `IntoResponseParts`, `Error`, `ResponseParts`, `TryIntoHeaderError`, `TryIntoHeaderErrorKind`

### 19. cors.mod

- **Target:** `layer.Cors [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101410.0
- **Functions:** 3/9 matched (target 14)
- **Missing functions:** `new`, `very_permissive`, `is_allow_credentials_any`, `handle_options_request`, `default`, `into_layer`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Service`, `Cors`, `Output`, `Error`

### 20. matcher.subdomain_trie

- **Target:** `matcher.SubdomainTrieMatcher`
- **Similarity:** 0.13
- **Dependents:** 0
- **Priority Score:** 50808.7
- **Functions:** 2/7 matched (target 4)
- **Missing functions:** `new`, `from_iter`, `test_trie_matching`, `test_path_matching_with_trie`, `test_non_matching_path`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/3 matched

### 21. remove_header.mod

- **Target:** `layer.RemoveHeader [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 40410.0
- **Functions:** 0/4 matched
- **Missing functions:** `remove_headers_by_prefix`, `remove_headers_by_exact_name`, `remove_hop_by_hop_request_headers`, `remove_hop_by_hop_response_headers`
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 22. web.mod

- **Target:** `web.Web [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 21)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 23. set_header.mod

- **Target:** `layer.SetHeader [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 8)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 24. convert.mod

- **Target:** `convert.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 25. auth.mod

- **Target:** `layer.Auth [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 26. body.mod

- **Target:** `body.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 27. redirect.mod

- **Target:** `redirect.Redirect [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 28. io.mod

- **Target:** `io.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Matched

| Source | Target | Path |
|--------|--------|------|
| `rama-http.lib` | `ramahttp.Lib` | `rama-http/src/lib` |

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `layer.mod` | `ramahttp.src.layer.Mod` | 0 | `rama-http/src/layer/mod.rs` | `ramahttp/src/layer/Mod.kt` |
| `client.mod` | `ramahttp.src.service.client.Mod` | 0 | `rama-http/src/service/client/mod.rs` | `ramahttp/src/service/client/Mod.kt` |
| `fs.mod` | `ramahttp.src.service.fs.Mod` | 0 | `rama-http/src/service/fs/mod.rs` | `ramahttp/src/service/fs/Mod.kt` |
| `serve_dir.mod` | `ramahttp.src.service.fs.servedir.Mod` | 0 | `rama-http/src/service/fs/serve_dir/mod.rs` | `ramahttp/src/service/fs/servedir/Mod.kt` |
| `service.mod` | `ramahttp.src.service.Mod` | 0 | `rama-http/src/service/mod.rs` | `ramahttp/src/service/Mod.kt` |
| `rama-http.service.web.endpoint.extract.body.mod` | `ramahttp.src.service.web.endpoint.extract.body.Mod` | 0 | `rama-http/src/service/web/endpoint/extract/body/mod.rs` | `ramahttp/src/service/web/endpoint/extract/body/Mod.kt` |
| `extract.mod` | `ramahttp.src.service.web.endpoint.extract.Mod` | 0 | `rama-http/src/service/web/endpoint/extract/mod.rs` | `ramahttp/src/service/web/endpoint/extract/Mod.kt` |
| `endpoint.mod` | `ramahttp.src.service.web.endpoint.Mod` | 0 | `rama-http/src/service/web/endpoint/mod.rs` | `ramahttp/src/service/web/endpoint/Mod.kt` |
| `rama-http.service.web.endpoint.response.mod` | `ramahttp.src.service.web.endpoint.response.Mod` | 0 | `rama-http/src/service/web/endpoint/response/mod.rs` | `ramahttp/src/service/web/endpoint/response/Mod.kt` |

