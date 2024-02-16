package entity

import kotlinx.serialization.Serializable

@Serializable
data class Chapter (

    val id: String,

    val type: String,

    val attributes: ChapterAttributes? = null,

    val relationships: List<Relationship>? = null

)