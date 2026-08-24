// port-lint: source layer/classify/mod.rs
package io.github.kotlinmania.ramahttp.layer

import io.github.kotlinmania.ramahttp.types.Response

public sealed class ClassifiedResponse {
    public object Success : ClassifiedResponse()
    public class Failure(public val reason: String) : ClassifiedResponse()

    public fun isSuccess(): Boolean = this is Success
    public fun isFailure(): Boolean = this is Failure
}

public fun interface ClassifyResponse {
    public fun classify(response: Response): ClassifiedResponse
}

public class ServerErrorsAsFailures : ClassifyResponse {
    override fun classify(response: Response): ClassifiedResponse {
        return if (response.status.isServerError()) {
            ClassifiedResponse.Failure("Server error: ${response.status}")
        } else {
            ClassifiedResponse.Success
        }
    }

    public companion object {
        public val DEFAULT: ServerErrorsAsFailures = ServerErrorsAsFailures()
    }
}
