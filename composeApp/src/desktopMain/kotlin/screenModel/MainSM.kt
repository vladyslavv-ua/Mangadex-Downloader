package screenModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dto.MangaDTO
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repo.ApiRepo

class MainSM(private val apiRepo: ApiRepo) : ScreenModel {
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private val _mangaList = MutableStateFlow<List<MangaDTO>>(emptyList())
    val mangaList = _mangaList.asStateFlow()



    init {
        screenModelScope.launch (Dispatchers.IO){
            _mangaList.value = apiRepo.getRecentManga()
        }
    }


    fun updateField(newString: String) {
        if (newString.contains("\n")){
            searchManga()
            return
        }
        _search.value = newString
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun searchManga() {
        screenModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _mangaList.value = apiRepo.searchManga(_search.value)
        }
    }
}