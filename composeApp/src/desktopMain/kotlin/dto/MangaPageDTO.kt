package dto


data class MangaPageDTO(
    val id: String,
    val name: String,
    val description: String,
    var cover: String,
    val altTitles: List<Map<String, String>>,
    val availableTranslatedLanguages: List<String>,
    val createdAt: String,
    val status: String
)
