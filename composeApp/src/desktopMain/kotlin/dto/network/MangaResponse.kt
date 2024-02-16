package dto.network

import entity.Manga
import kotlinx.serialization.Serializable

@Serializable
data class MangaResponse (
    val result: String,
    val response: String? = "entity",
    val `data`: Manga? = null
)
