package entity

import kotlinx.serialization.Serializable

@Serializable
data class Cover(

    val id: String? = null,


    val type: String? = null,


    val attributes: CoverAttributes? = null,


    val relationships: List<Relationship>? = null
)