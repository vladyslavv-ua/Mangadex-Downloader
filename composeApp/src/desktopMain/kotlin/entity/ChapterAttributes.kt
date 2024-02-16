package entity

import kotlinx.serialization.Serializable

@Serializable
data class ChapterAttributes(

    val title: String? = null,

    val volume: String? = null,

    val chapter: String? = null,

    /* Count of readable images for this chapter */
    val pages: Int? = null,

    val translatedLanguage: String? = null,

    val uploader: String? = null,

    /* Denotes a chapter that links to an external source. */
    val externalUrl: String? = null,

    val version: Int? = null,

    val createdAt: String? = null,

    val updatedAt: String? = null,

    val publishAt: String? = null,
    val readableAt: String? = null

)

