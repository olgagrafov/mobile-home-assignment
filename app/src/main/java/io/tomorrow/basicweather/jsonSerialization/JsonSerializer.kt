package io.tomorrow.basicweather.jsonSerialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

internal class JsonSerializer : IJsonSerializer {

    private val json by lazy { getJsonInstance() }

    override fun getJsonInstance(): Json {
        return Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun <ResultType> decodeFromStringOrThrow(
        string: String,
        serializer: KSerializer<ResultType>,
        errorVerbosity: IJsonSerializer.ErrorVerbosity
    ): ResultType {
        return try {
            json.decodeFromString(serializer, string)
        } catch (throwable: Throwable) {
            val typeInformation = serializer.descriptor.serialName
            val errorMessageBuilder = StringBuilder("Failed to decode string to type [$typeInformation].")
            when (errorVerbosity) {
                IJsonSerializer.ErrorVerbosity.IncludeSource -> {
                    errorMessageBuilder.append(" Encoded string [$string].")
                }
                IJsonSerializer.ErrorVerbosity.ExcludeSource -> {}
            }
            throw DecodingException(errorMessageBuilder.toString(), throwable)
        }
    }

    override fun <T : Any> encodeToStringOrThrow(
        value: T,
        serializer: KSerializer<T>,
        errorVerbosity: IJsonSerializer.ErrorVerbosity
    ): String {
        return try {
            json.encodeToString(serializer, value)
        } catch (throwable: Throwable) {
            val typeInformation = value::class.qualifiedName
            val errorMessageBuilder = StringBuilder("Failed to encode string from type [$typeInformation].")
            when (errorVerbosity) {
                IJsonSerializer.ErrorVerbosity.IncludeSource -> {
                    errorMessageBuilder.append(" String to encode [$value].")
                }
                IJsonSerializer.ErrorVerbosity.ExcludeSource -> {}
            }
            throw EncodingException(errorMessageBuilder.toString(), throwable)
        }
    }
}
