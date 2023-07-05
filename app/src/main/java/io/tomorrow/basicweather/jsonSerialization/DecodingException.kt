package io.tomorrow.basicweather.jsonSerialization

sealed class SerializationError(message: String, cause: Throwable) : Exception(message, cause)
class DecodingException(message: String, cause: Throwable) : SerializationError(message, cause)
class EncodingException(message: String, cause: Throwable) : SerializationError(message, cause)
