package dto.network

import entity.Manga
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val result: String,
    val response: String,
    val data: List<Manga>,
    val limit:Int,
    val offset: Int,
    val total: Int
)