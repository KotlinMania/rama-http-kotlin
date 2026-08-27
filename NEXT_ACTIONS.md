# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 0/205 (0.0%)
- **Function parity:** 0/2067 matched — 0.0%
- **Class/type parity:** 0/646 matched — 0.0%
- **Combined symbol parity:** 0/2713 matched — 0.0%
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

3. **body.bytes** (17 deps)
   - Path: `service/web/endpoint/extract/body/bytes.rs`
   - Essential for 17 other files

4. **response.into_response** (17 deps)
   - Path: `service/web/endpoint/response/into_response.rs`
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

