package dataSource.pagingSource

import DEFAULT_PAGE_LIMIT
import androidx.paging.PagingState
import app.cash.paging.PagingSource
import dataSource.ApiSource
import entity.Chapter
import kotlinx.serialization.SerializationException

class ChapterPagingSource(private val apiSource: ApiSource, private val id: String, private val lang: String) :
    PagingSource<Int, Chapter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chapter> {

        try {
            val currentPage = params.key ?: 0
            println(currentPage)
            val offset = currentPage + DEFAULT_PAGE_LIMIT
            val chapters = apiSource.getMangaChapters(
                id = id,
                lang = lang,
                offset = currentPage
            )
            return if (chapters == null) {
                LoadResult.Error(Throwable("some error"))
            } else {
                val nextKey = if ((chapters.total ?: 0) < offset) currentPage + 1 else null
                println("NEXT KEY IS $nextKey")
                println("TOTAL IS  ${chapters.total}")
                println("OFFSET IS $offset")
                println("CURRENT PAGE IS $currentPage")

                LoadResult.Page(
                    data = chapters.data ?: emptyList(),
                    prevKey = null,
                    nextKey = nextKey
                )
            }

        } catch (exception: SerializationException) {
            println(exception.cause)
            return LoadResult.Error(exception)

        } catch (exception: Exception) {
            println(exception)
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Chapter>): Int? {
//        return state.anchorPosition
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
