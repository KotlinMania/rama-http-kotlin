# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 0/205 (0.0%)
- **Function parity:** 0/1682 matched — 0.0%
- **Class/type parity:** 0/550 matched — 0.0%
- **Combined symbol parity:** 0/2232 matched — 0.0%
- **Average inline-code cosine:** 0.00 (function body across 0 matched files)
- **Average documentation cosine:** 0.00 (doc text across 0 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **har.service** (28 deps)
   - Path: `layer/har/service.rs`
   - Essential for 28 other files

2. **io.request** (22 deps)
   - Path: `io/request.rs`
   - Essential for 22 other files

3. **response.into_response** (17 deps)
   - Path: `service/web/endpoint/response/into_response.rs`
   - Essential for 17 other files

4. **body.bytes** (17 deps)
   - Path: `service/web/endpoint/extract/body/bytes.rs`
   - Essential for 17 other files

5. **har.layer** (13 deps)
   - Path: `layer/har/layer.rs`
   - Essential for 13 other files

6. **har.extensions** (10 deps)
   - Path: `layer/har/extensions.rs`
   - Essential for 10 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

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

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `body.mod` | `body.Mod` | 0 | `body/mod.rs` | `body/Mod.kt` |
| `convert.mod` | `convert.Mod` | 0 | `convert/mod.rs` | `convert/Mod.kt` |
| `io.mod` | `io.Mod` | 0 | `io/mod.rs` | `io/Mod.kt` |
| `auth.mod` | `layer.auth.Mod` | 0 | `layer/auth/mod.rs` | `layer/auth/Mod.kt` |
| `classify.mod` | `layer.classify.Mod` | 0 | `layer/classify/mod.rs` | `layer/classify/Mod.kt` |
| `compression.mod` | `layer.compression.Mod` | 0 | `layer/compression/mod.rs` | `layer/compression/Mod.kt` |
| `stream.mod` | `layer.compression.stream.Mod` | 0 | `layer/compression/stream/mod.rs` | `layer/compression/stream/Mod.kt` |
| `cors.mod` | `layer.cors.Mod` | 0 | `layer/cors/mod.rs` | `layer/cors/Mod.kt` |
| `decompression.mod` | `layer.decompression.Mod` | 0 | `layer/decompression/mod.rs` | `layer/decompression/Mod.kt` |
| `layer.decompression.request.mod` | `layer.decompression.request.Mod` | 0 | `layer/decompression/request/mod.rs` | `layer/decompression/request/Mod.kt` |
| `dns_resolve.mod` | `layer.dns.dnsresolve.Mod` | 0 | `layer/dns/dns_resolve/mod.rs` | `layer/dns/dnsresolve/Mod.kt` |
| `dns.mod` | `layer.dns.Mod` | 0 | `layer/dns/mod.rs` | `layer/dns/Mod.kt` |
| `follow_redirect.mod` | `layer.followredirect.Mod` | 0 | `layer/follow_redirect/mod.rs` | `layer/followredirect/Mod.kt` |
| `policy.mod` | `layer.followredirect.policy.Mod` | 0 | `layer/follow_redirect/policy/mod.rs` | `layer/followredirect/policy/Mod.kt` |
| `forwarded.mod` | `layer.forwarded.Mod` | 0 | `layer/forwarded/mod.rs` | `layer/forwarded/Mod.kt` |
| `har.mod` | `layer.har.Mod` | 0 | `layer/har/mod.rs` | `layer/har/Mod.kt` |
| `recorder.mod` | `layer.har.recorder.Mod` | 0 | `layer/har/recorder/mod.rs` | `layer/har/recorder/Mod.kt` |
| `match_redirect.mod` | `layer.matchredirect.Mod` | 0 | `layer/match_redirect/mod.rs` | `layer/matchredirect/Mod.kt` |
| `layer.mod` | `layer.Mod` | 0 | `layer/mod.rs` | `layer/Mod.kt` |
| `remove_header.mod` | `layer.removeheader.Mod` | 0 | `layer/remove_header/mod.rs` | `layer/removeheader/Mod.kt` |
| `required_header.mod` | `layer.requiredheader.Mod` | 0 | `layer/required_header/mod.rs` | `layer/requiredheader/Mod.kt` |
| `retry.mod` | `layer.retry.Mod` | 0 | `layer/retry/mod.rs` | `layer/retry/Mod.kt` |
| `rewrite_uri.mod` | `layer.rewriteuri.Mod` | 0 | `layer/rewrite_uri/mod.rs` | `layer/rewriteuri/Mod.kt` |
| `set_header.mod` | `layer.setheader.Mod` | 0 | `layer/set_header/mod.rs` | `layer/setheader/Mod.kt` |
| `request.mod` | `layer.setheader.request.Mod` | 0 | `layer/set_header/request/mod.rs` | `layer/setheader/request/Mod.kt` |
| `response.mod` | `layer.setheader.response.Mod` | 0 | `layer/set_header/response/mod.rs` | `layer/setheader/response/Mod.kt` |
| `timeout.mod` | `layer.timeout.Mod` | 0 | `layer/timeout/mod.rs` | `layer/timeout/Mod.kt` |
| `trace.mod` | `layer.trace.Mod` | 0 | `layer/trace/mod.rs` | `layer/trace/Mod.kt` |
| `traffic_writer.mod` | `layer.trafficwriter.Mod` | 0 | `layer/traffic_writer/mod.rs` | `layer/trafficwriter/Mod.kt` |
| `util.mod` | `layer.util.Mod` | 0 | `layer/util/mod.rs` | `layer/util/Mod.kt` |
| `validate_request.mod` | `layer.validaterequest.Mod` | 0 | `layer/validate_request/mod.rs` | `layer/validaterequest/Mod.kt` |
| `version_adapter.mod` | `layer.versionadapter.Mod` | 0 | `layer/version_adapter/mod.rs` | `layer/versionadapter/Mod.kt` |
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |
| `matcher.mod` | `matcher.Mod` | 0 | `matcher/mod.rs` | `matcher/Mod.kt` |
| `path.mod` | `matcher.path.Mod` | 0 | `matcher/path/mod.rs` | `matcher/path/Mod.kt` |
| `client.mod` | `service.client.Mod` | 0 | `service/client/mod.rs` | `service/client/Mod.kt` |
| `fs.mod` | `service.fs.Mod` | 0 | `service/fs/mod.rs` | `service/fs/Mod.kt` |
| `serve_dir.mod` | `service.fs.servedir.Mod` | 0 | `service/fs/serve_dir/mod.rs` | `service/fs/servedir/Mod.kt` |
| `service.mod` | `service.Mod` | 0 | `service/mod.rs` | `service/Mod.kt` |
| `redirect.mod` | `service.redirect.Mod` | 0 | `service/redirect/mod.rs` | `service/redirect/Mod.kt` |
| `service.web.endpoint.extract.body.mod` | `service.web.endpoint.extract.body.Mod` | 0 | `service/web/endpoint/extract/body/mod.rs` | `service/web/endpoint/extract/body/Mod.kt` |
| `extract.mod` | `service.web.endpoint.extract.Mod` | 0 | `service/web/endpoint/extract/mod.rs` | `service/web/endpoint/extract/Mod.kt` |
| `endpoint.mod` | `service.web.endpoint.Mod` | 0 | `service/web/endpoint/mod.rs` | `service/web/endpoint/Mod.kt` |
| `service.web.endpoint.response.mod` | `service.web.endpoint.response.Mod` | 0 | `service/web/endpoint/response/mod.rs` | `service/web/endpoint/response/Mod.kt` |
| `web.mod` | `service.web.Mod` | 0 | `service/web/mod.rs` | `service/web/Mod.kt` |
| `macros.mod` | `utils.macros.Mod` | 0 | `utils/macros/mod.rs` | `utils/macros/Mod.kt` |
| `utils.mod` | `utils.Mod` | 0 | `utils/mod.rs` | `utils/Mod.kt` |

