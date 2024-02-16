package entity

import kotlinx.serialization.Serializable

@Serializable
data class MangaAttributes(

    val title: Map<String, String>? = null,


    val altTitles: List<Map<String, String>>? = null,


    val description: Map<String, String>? = null,


    val isLocked: Boolean? = null,


    val links: Map<String, String>? = null,


    val originalLanguage: String? = null,


    val lastVolume: String? = null,


    val lastChapter: String? = null,


    val publicationDemographic: PublicationDemographic? = null,


    val status: Status? = null,

    val year: Int? = null,


    val contentRating: ContentRating? = null,


    val chapterNumbersResetOnNewVolume: Boolean? = null,


    val availableTranslatedLanguages: List<String> ?= null,


    val latestUploadedChapter: String? = null,


    val tags: List<Tag>? = null,


    val state: State? = null,


    val version: Int? = null,


    val createdAt: String? = null,


    val updatedAt: String? = null
){

    enum class PublicationDemographic(val value: String) {
         shounen("shounen"),
         shoujo("shoujo"),
         josei("josei"),
        seinen("seinen");
    }

    enum class Status(val value: String) {
        completed("completed"),
        ongoing("ongoing"),
        cancelled("cancelled"),
        hiatus("hiatus");
    }

    enum class ContentRating(val value: String) {
        safe("safe"),
        suggestive("suggestive"),
        erotica("erotica"),
        pornographic("pornographic");
    }

    enum class State(val value: String) {
        draft("draft"),
        submitted("submitted"),
        published("published"),
        rejected("rejected");
    }
}
