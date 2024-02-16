package entity

import kotlinx.serialization.Serializable

@Serializable
data class Tag(


    val id: String? = null,


    val type: Type? = null,


    val attributes: TagAttributes? = null,


    val relationships: List<Relationship>? = null

) {

    enum class Type(val value: String) {

        tag("tag");
    }
}

