package screenModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dto.MangaPageDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repo.ApiRepo

class MangaSM(private val apiRepo: ApiRepo, private val mangaId: String) : ScreenModel {
    private val _mangaScreenInfo = MutableStateFlow(
        MangaPageDTO(
            "", "", "", "",
            emptyList(), emptyList(), "", ""
        )
    )
    val mangaScreenInfo = _mangaScreenInfo.asStateFlow()

    private val _loadingState = MutableStateFlow(true)
    val loadingState = _loadingState.asStateFlow()

    init {
        println(mangaId)
        screenModelScope.launch(Dispatchers.IO) {

            val mangaInfo = apiRepo.getMangaById(mangaId)
            if (mangaInfo != null) {
                _mangaScreenInfo.value = mangaInfo
                println(mangaInfo.availableTranslatedLanguages)
            }
            _loadingState.value = false
        }
    }

    fun getMangaDetails() {
        println("mangaId")
        println(mangaId)
    }

}