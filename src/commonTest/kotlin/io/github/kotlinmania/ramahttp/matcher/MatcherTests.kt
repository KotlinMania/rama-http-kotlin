// port-lint: tests matcher/mod.rs
package io.github.kotlinmania.ramahttp.matcher

import io.github.kotlinmania.ramahttp.core.Extensions
import io.github.kotlinmania.ramahttp.types.Body
import io.github.kotlinmania.ramahttp.types.HeaderName
import io.github.kotlinmania.ramahttp.types.HeaderValue
import io.github.kotlinmania.ramahttp.types.Method
import io.github.kotlinmania.ramahttp.types.Request
import io.github.kotlinmania.ramahttp.types.Version
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MatcherTests {

    @Test
    fun testMethodMatcher() {
        val getOrPost = MethodMatcher.GET.or(MethodMatcher.POST)
        assertTrue(getOrPost.contains(MethodMatcher.GET))
        assertTrue(getOrPost.contains(MethodMatcher.POST))
        assertFalse(getOrPost.contains(MethodMatcher.DELETE))

        val reqGet = Request.builder().method(Method.GET).body(Body.empty())
        val reqDelete = Request.builder().method(Method.DELETE).body(Body.empty())

        assertTrue(getOrPost.matches(null, reqGet))
        assertFalse(getOrPost.matches(null, reqDelete))
    }

    @Test
    fun testVersionMatcher() {
        val h1OrH2 = VersionMatcher.HTTP_11.or(VersionMatcher.HTTP_2)
        assertTrue(h1OrH2.contains(VersionMatcher.HTTP_11))
        assertTrue(h1OrH2.contains(VersionMatcher.HTTP_2))
        assertFalse(h1OrH2.contains(VersionMatcher.HTTP_3))
    }

    @Test
    fun testHeaderMatcher() {
        val exists = HeaderMatcher.exists(HeaderName.AUTHORIZATION)
        val isJson = HeaderMatcher.`is`(HeaderName.CONTENT_TYPE, HeaderValue.fromString("application/json"))

        val req = Request.builder()
            .header(HeaderName.CONTENT_TYPE, HeaderValue.fromString("application/json"))
            .body(Body.empty())

        assertFalse(exists.matches(null, req))
        assertTrue(isJson.matches(null, req))
    }

    @Test
    fun testDomainMatcher() {
        val exact = DomainMatcher.exact("example.com")
        val sub = DomainMatcher.sub("example.com")

        assertTrue(exact.isMatch("example.com"))
        assertFalse(exact.isMatch("sub.example.com"))

        assertTrue(sub.isMatch("example.com"))
        assertTrue(sub.isMatch("api.example.com"))
        assertTrue(sub.isMatch("foo.bar.example.com"))
        assertFalse(sub.isMatch("notexample.com"))
    }

    @Test
    fun testSubdomainTrieMatcher() {
        val trie = SubdomainTrieMatcher.of("example.com", "google.com")
        assertTrue(trie.isMatch("example.com"))
        assertTrue(trie.isMatch("sub.example.com"))
        assertTrue(trie.isMatch("mail.google.com"))
        assertFalse(trie.isMatch("apple.com"))
    }

    @Test
    fun testUriMatcher() {
        val wildcard = UriMatcher.wildcard("http://example.com/users/*")
        assertTrue(wildcard.isMatch("http://example.com/users/123"))
        assertFalse(wildcard.isMatch("http://example.com/posts/123"))
    }

    @Test
    fun testPathMatcherParams() {
        val matcher = PathMatcher.new("/users/:id/posts/:post_id")
        val ext = Extensions()
        val matched = matcher.matchesPath(ext, "/users/42/posts/99")

        assertTrue(matched)
        val params = ext.get<UriParams>()
        assertNotNull(params)
        assertEquals("42", params.get("id"))
        assertEquals("99", params.get("post_id"))
    }

    @Test
    fun testPathMatcherGlob() {
        val matcher = PathMatcher.new("/static/*")
        val ext = Extensions()
        val matched = matcher.matchesPath(ext, "/static/css/app.css")

        assertTrue(matched)
        val params = ext.get<UriParams>()
        assertNotNull(params)
        assertEquals("/css/app.css", params.glob)
    }

    @Test
    fun testHttpMatcherComposite() {
        val matcher = HttpMatcher.methodGet().andPath("/api/v1/health")
        val validReq = Request.builder().method(Method.GET).uri("/api/v1/health").body(Body.empty())
        val invalidMethod = Request.builder().method(Method.POST).uri("/api/v1/health").body(Body.empty())
        val invalidPath = Request.builder().method(Method.GET).uri("/api/v1/other").body(Body.empty())

        assertTrue(matcher.matches(validReq.extensions, validReq))
        assertFalse(matcher.matches(invalidMethod.extensions, invalidMethod))
        assertFalse(matcher.matches(invalidPath.extensions, invalidPath))
    }
}
