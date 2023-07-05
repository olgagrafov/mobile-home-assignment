package io.tomorrow.basicweather.network.ktorNetworkCallExecutor

import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.tomorrow.basicweather.network.NetworkError

internal interface IHttpResponseValidator {
    fun validateStatusCode(httpStatusCode: HttpStatusCode, responseBody: String, headers: Headers)
}

internal class HttpResponseValidator : IHttpResponseValidator {
    override fun validateStatusCode(httpStatusCode: HttpStatusCode, responseBody: String, headers: Headers) {
        if (httpStatusCode.isSuccess()) return

        val statusCodeValue = httpStatusCode.value
        val description = httpStatusCode.description
        when (statusCodeValue) {
            400 -> throw NetworkError.BadRequest(statusCodeValue, description, responseBody, headers.entries())
            401 -> throw NetworkError.Unauthorized(statusCodeValue, description, responseBody, headers.entries())
            403 -> throw NetworkError.Forbidden(statusCodeValue, description, responseBody, headers.entries())
            404 -> throw NetworkError.NotFound(statusCodeValue, description, responseBody, headers.entries())
            500 -> throw NetworkError.InternalServerError(statusCodeValue, description, responseBody, headers.entries())
            else -> throw NetworkError.Unknown(statusCodeValue, description, responseBody, headers.entries())
        }
    }
}
