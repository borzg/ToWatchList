package com.borzg.data

import android.util.Log
import androidx.paging.PagingSource
import com.borzg.domain.model.search.SearchResult
import com.borzg.domain.repository.CinemaSearchRepository

private const val START_POSITION = 1
const val TMDB_PAGE_SIZE = 20

class MultiSearchSource(val repository: CinemaSearchRepository, val query: String) : PagingSource<Int, SearchResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {
        val position = params.key ?: START_POSITION
        return try {
            val results = repository.getMultiSearchResult(query, position)
            Log.d("TAG", "load: $results")
            LoadResult.Page(
                data = results,
                prevKey = if (position == START_POSITION) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (throwable: Throwable) {
            Log.d("TAG", "load: error: ${throwable.message}")
            return LoadResult.Error(throwable)
        }
    }
}