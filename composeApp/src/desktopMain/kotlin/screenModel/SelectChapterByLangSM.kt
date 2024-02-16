package screenModel

import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import entity.Chapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import repo.ApiRepo

class SelectChapterByLangSM(private val apiRepo: ApiRepo, private val mangaId: String, private val lang: String) :
    ScreenModel {
    private val _chapters: MutableStateFlow<PagingData<Chapter>> = MutableStateFlow(value = PagingData.empty())
    val chapters: MutableStateFlow<PagingData<Chapter>> get() = _chapters


    init {
        getContentInfoByLang(lang)
    }

    fun getContentInfoByLang(lang: String) {
        screenModelScope.launch(Dispatchers.IO) {

            apiRepo.getChapters(mangaId, lang).cachedIn(screenModelScope).collect {
                _chapters.value = it
            }
        }

    }
}