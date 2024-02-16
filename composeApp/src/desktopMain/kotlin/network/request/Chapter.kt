package network.request

import io.ktor.resources.*
import kotlinx.serialization.SerialName

@Resource("/chapter")
class Chapter {
    @Resource("")
    class GetByIdAndLang(
        val manga: String,
        @SerialName("translatedLanguage[]")
        val translatedLanguage: List<String>,
        val limit: Int,
        val offset: Int,
        val parent: Chapter = Chapter()
    )
}