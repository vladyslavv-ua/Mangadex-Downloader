package dto.network

import entity.Cover
import kotlinx.serialization.Serializable

@Serializable
data class CoverResponse (


    val result: String? = null,


    val response: String? = "entity",


    val `data`: Cover? = null

)
