package io.tomorrow.basicweather.jsonSerialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

interface IJsonSerializer {
    fun getJsonInstance(): Json

    fun <ResultType> decodeFromStringOrThrow(
        string: String,
        serializer: KSerializer<ResultType>,
        errorVerbosity: ErrorVerbosity = ErrorVerbosity.ExcludeSource
    ): ResultType

    fun <T : Any> encodeToStringOrThrow(
        value: T,
        serializer: KSerializer<T>,
        errorVerbosity: ErrorVerbosity = ErrorVerbosity.ExcludeSource
    ): String

    enum class ErrorVerbosity {
        IncludeSource,
        ExcludeSource
    }
}
