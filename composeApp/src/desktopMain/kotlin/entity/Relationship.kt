package entity

import kotlinx.serialization.Serializable


@Serializable
data class Relationship (

    val id: String? = null,

    val type: String? = null,

    /* Related Manga type, only present if you are on a Manga entity and a Manga relationship */
    val related: String? = null,

    /* If Reference Expansion is applied, contains objects attributes */
    val attributes: List<String>? = null

)

