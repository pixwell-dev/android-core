package sk.pixwell.android.core.sample.githubbrowser.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val count: LiveData<Int>,
        val isLoading: LiveData<Boolean>,
        val refresh: () -> Unit
)