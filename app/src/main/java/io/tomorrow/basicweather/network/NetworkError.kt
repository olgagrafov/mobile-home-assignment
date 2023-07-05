package io.tomorrow.basicweather.network

sealed class NetworkError(
    val statusCodeValue: Int,
    val description: String,
    responseBody: String,
    headers: Set<Map.Entry<String, List<String>>>
) : Exception("A network error occurred with status code $statusCodeValue - $description. Response body: [$responseBody]. Headers: $headers.") {

    class BadRequest(
        statusCodeValue: Int,
        description: String,
        responseBody: String,
        headers: Set<Map.Entry<String, List<String>>>
    ) : NetworkError(statusCodeValue, description, responseBody, headers)

    class Unauthorized(
        statusCodeValue: Int,
        description: String,
        responseBody: String,
        headers: Set<Map.Entry<String, List<String>>>
    ) : NetworkError(statusCodeValue, description, responseBody, headers)

    class Forbidden(
        statusCodeValue: Int,
        description: String,
        responseBody: String,
        headers: Set<Map.Entry<String, List<String>>>
    ) : NetworkError(statusCodeValue, description, responseBody, headers)

    class NotFound(
        statusCodeValue: Int,
        description: String,
        responseBody: String,
        headers: Set<Map.Entry<String, List<String>>>
    ) : NetworkError(statusCodeValue, description, responseBody, headers)

    class InternalServerError(
        statusCodeValue: Int,
        description: String,
        responseBody: String,
        headers: Set<Map.Entry<String, List<String>>>
    ) : NetworkError(statusCodeValue, description, responseBody, headers)

    class Unknown(
        statusCodeValue: Int,
        description: String,
        responseBody: String,
        headers: Set<Map.Entry<String, List<String>>>
    ) : NetworkError(statusCodeValue, description, responseBody, headers)
}
