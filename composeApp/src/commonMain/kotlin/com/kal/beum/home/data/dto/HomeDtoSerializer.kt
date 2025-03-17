package com.kal.beum.home.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object HomeDtoSerializer : KSerializer<HomeCommentDto> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        HomeCommentDto::class.simpleName!!
    ) {
        element<String?>("description")
    }

    override fun deserialize(decoder: Decoder): HomeCommentDto =
        decoder.decodeStructure(descriptor) {
            var id: Int = -1
            var comment = ""
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> {
                        id = decodeIntElement(descriptor, 0) // id 필드 처리
                    }

                    1 -> {
                        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                            "This decoder only works with JSON."
                        )
                        val element = jsonDecoder.decodeJsonElement()
                        comment = if (element is JsonObject) {
                            decoder.json.decodeFromJsonElement<HomeCommentDto>(
                                element = element, deserializer = HomeCommentDto.serializer()
                            ).comment
                        } else if (element is JsonPrimitive && element.isString) {
                            element.content
                        } else ""
                    }

                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index $index")
                }
            }
            HomeCommentDto(id, comment)
        }

    override fun serialize(encoder: Encoder, value: HomeCommentDto) =
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.id) // id 필드 직렬화
            encodeStringElement(descriptor, 1, value.comment) // comment 필드 직렬화
        }
}