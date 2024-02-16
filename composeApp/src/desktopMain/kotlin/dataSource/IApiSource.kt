package dataSource

import dto.network.CoverResponse
import dto.network.SearchResponse
import dto.MangaPageDTO
import dto.network.ChapterListResponse

interface IApiSource {
    suspend fun search(title: String): SearchResponse?
    suspend fun getRecentManga(): SearchResponse?
    suspend fun getCover(id: String): CoverResponse?
    suspend fun getManga(id: String): MangaPageDTO?
    suspend fun getMangaChapters(id: String, lang: String, offset: Int): ChapterListResponse?
}