package dto.network

import entity.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class ChapterListResponse(
    val result: String? = "ok",

    val response: String? = "collection",

    val `data`: List<Chapter>? = null,

    val limit: Int? = null,

    val offset: Int? = null,

    val total: Int? = null
)
