package repo

import DEFAULT_PAGE_LIMIT
import androidx.paging.Pager
import androidx.paging.PagingData
import app.cash.paging.PagingConfig
import dto.network.SearchResponse
import dto.MangaDTO
import dto.MangaPageDTO
import entity.Manga
import kotlinx.coroutines.*
import dataSource.ApiSource
import dataSource.DBSource
import dataSource.pagingSource.ChapterPagingSource
import entity.Chapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.util.Collections.synchronizedList

class ApiRepo(private val apiSource: ApiSource, private val dbSource: DBSource) {
    suspend fun searchManga(title: String): List<MangaDTO> {
        val resp = apiSource.search(title)
        return processManga(resp)
    }

    suspend fun getRecentManga(): List<MangaDTO> {
        val resp = apiSource.getRecentManga()
        return processManga(resp)
    }

    suspend fun getMangaById(id: String): MangaPageDTO? {
        val cachedManga = dbSource.getManga(id)
        if (cachedManga != null) {
            return cachedManga
        }
        val response = apiSource.getManga(id)
        if (response != null) {
            val cover = getCover(response.cover)
            response.cover = cover
            dbSource.insertManga(response)
            return response
        }
        return null
    }

    fun getChapters(id: String, lang: String): Flow<PagingData<Chapter>> = Pager(
        PagingConfig(Int.MAX_VALUE),
        pagingSourceFactory = {
            ChapterPagingSource(apiSource, id, lang)
        }
    ).flow


    private suspend fun getCover(id: String): String {
        val resp = apiSource.getCover(id)
        if (resp != null) {
            return resp.data?.attributes?.fileName ?: ""
        }
        return ""
    }

    private suspend fun processManga(response: SearchResponse?): List<MangaDTO> {
        if (response != null) {
            val mangaList = synchronizedList(mutableListOf<MangaDTO>())
            println("start")
            coroutineScope {
                response.data.forEach { manga: Manga ->
                    val id = manga.id
                    val cachedManga = dbSource.getManga(id)
                    if (cachedManga != null) {
                        val mangaDTO = MangaDTO(cachedManga.name, cachedManga.id, coverId = cachedManga.cover)
                        mangaList.add(mangaDTO)
                    } else {
                        launch(Dispatchers.IO) {
                            println("launching " + manga.attributes?.title)
                            val name = manga.attributes?.title?.values?.first() ?: ""
                            val cover = manga.relationships?.first {
                                it.type == "cover_art"
                            }?.id ?: ""
                            val coverFileName = getCover(cover)
                            val mangaDTO = MangaDTO(name, id, coverId = coverFileName)
                            mangaList.add(mangaDTO)
                        }
                    }
                }
            }
            return mangaList.toList()
        }
        return emptyList()
    }
}