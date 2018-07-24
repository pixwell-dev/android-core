package sk.pixwell.android.core.sample.githubbrowser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import sk.pixwell.android.core.domain.usecase.UseCase
import sk.pixwell.android.core.sample.githubbrowser.data.repository.Listing
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel
import sk.pixwell.android.core.sample.githubbrowser.domain.usecase.repo.GetReposUseCase

class GithubBrowserViewModel : ViewModel() {
    private val getRepos = GetReposUseCase()
    private val getReposResult = MutableLiveData<Listing<RepoModel>>()

    val repos: LiveData<PagedList<RepoModel>> =
            Transformations.switchMap(getReposResult) { it.pagedList }
    val loading: LiveData<Boolean> = Transformations.switchMap(getReposResult, { it.isLoading })
    val isEmpty: LiveData<Boolean> =
            Transformations.switchMap(getReposResult) { Transformations.map(it.count, { it == 0 }) }

    init {
        getRepos.subscribe(UseCase.Params()) { result ->
            result.fold({
                //e { "getCurrentEvents error: $it" }
            }, {
                getReposResult.value = it
            })
        }
    }

    fun refresh() {
        getReposResult.value?.refresh?.invoke()
    }

    override fun onCleared() {
        getRepos.clear()
        super.onCleared()
    }
}