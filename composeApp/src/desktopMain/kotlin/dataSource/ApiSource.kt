package dataSource

import DEFAULT_PAGE_LIMIT
import androidx.paging.DEBUG
import androidx.paging.PagingState
import androidx.paging.log
import app.cash.paging.PagingSource
import network.request.Manga
import dto.network.CoverResponse
import dto.network.MangaResponse
import dto.network.SearchResponse
import dto.MangaPageDTO
import dto.network.ChapterListResponse
import network.request.Cover
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.statement.*
import io.ktor.http.*
import network.request.Chapter


class ApiSource(private val httpClient: HttpClient) : IApiSource {
    override suspend fun search(title: String): SearchResponse? {
        val response = httpClient.get(Manga.Search(title = title))
        if (response.status.value == 200) {
            return response.body()
        }
        return null
    }

    override suspend fun getRecentManga(): SearchResponse? {
        val response = httpClient.get(Manga())
        if (response.status.value == 200) {
            return response.body()
        }
        return null
    }

    override suspend fun getCover(id: String): CoverResponse? {
        val response = httpClient.get(Cover(id))
        if (response.status.value == 200) {
            return response.body()
        }
        return null
    }

    override suspend fun getManga(id: String): MangaPageDTO? {
        val response = httpClient.get(Manga.GetById(id = id))
        if (response.status.value == 200) {
            val mangaResponse: MangaResponse = response.body()
            val mangaId = mangaResponse.data?.id ?: ""
            val name = mangaResponse.data?.attributes?.title?.values?.first() ?: ""
            val coverId = mangaResponse.data?.relationships?.first {
                it.type == "cover_art"
            }?.id ?: ""
            println(mangaResponse.data?.attributes?.description)
            val description = mangaResponse.data?.attributes?.description?.values?.firstOrNull() ?: ""
            val altTitles = mangaResponse.data?.attributes?.altTitles ?: emptyList()
            val availableTranslations = mangaResponse.data?.attributes?.availableTranslatedLanguages ?: emptyList()
            val createdAt = mangaResponse.data?.attributes?.createdAt ?: ""
            val status = mangaResponse.data?.attributes?.status?.value ?: ""
            return MangaPageDTO(
                mangaId, name, description,
                coverId, altTitles, availableTranslations, createdAt, status
            )
        }
        return null
    }


    override suspend fun getMangaChapters(
        id: String,
        lang: String,
        offset: Int
    ): ChapterListResponse? {
        val response = httpClient.get(Chapter.GetByIdAndLang(id, listOf(lang), DEFAULT_PAGE_LIMIT, offset))
        log(level = DEBUG, Throwable(response.request.url.toString())){
            response.request.url.toString()
        }
        println("I AM BEIGN CALLING ${response.request.url}")

        if (response.status.value == 200) {
            val mapped: ChapterListResponse = response.body()
            return mapped
        }
        println(response.bodyAsText())
        return null
    }


}