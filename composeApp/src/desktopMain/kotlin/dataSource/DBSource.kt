package dataSource

import adapter.ListMapStringStringToString
import adapter.ListStringToString
import app.cash.sqldelight.db.SqlDriver
import dto.MangaPageDTO
import io.vladyslavv_ua.sql.Cache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import migrations.Manga

class DBSource(driver: SqlDriver) {
    private val cache = Cache(
        driver,
        mangaAdapter = Manga.Adapter(
            availableTranslatedLanguagesAdapter = ListStringToString(),
            altTitlesAdapter = ListMapStringStringToString()
        )
    )


    suspend fun insertManga(manga: MangaPageDTO) = withContext(Dispatchers.Default) {
        cache.mangaQueriesQueries.insert(
            manga.id,
            manga.name,
            manga.description,
            manga.cover,
            manga.altTitles,
            manga.availableTranslatedLanguages,
            manga.status,
            manga.createdAt
        )
    }

    suspend fun getManga(id: String): MangaPageDTO? = withContext(Dispatchers.Default) {
        val cachedManga = cache.mangaQueriesQueries.search(id).executeAsList()
        if (cachedManga.isNotEmpty()) {
            val manga = cachedManga.first()
            return@withContext MangaPageDTO(
                manga.id,
                manga.name,
                manga.description,
                manga.cover,
                manga.altTitles,
                manga.availableTranslatedLanguages,
                manga.createdAt,
                manga.status
            )
        }
        return@withContext null
    }
}