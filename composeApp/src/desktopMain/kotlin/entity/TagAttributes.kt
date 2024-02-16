package entity

import kotlinx.serialization.Serializable

@Serializable
data class TagAttributes (

    val name: Map<String, String>? = null,

    val description: Map<String, String>? = null,

    val group: Group? = null,

    val version: Int? = null

) {

    /**
     *
     *
     * Values: content,format,genre,theme
     */
    enum class Group(val value: String) {
        content("content"),
        format("format"),
        genre("genre"),
        theme("theme");
    }
}

