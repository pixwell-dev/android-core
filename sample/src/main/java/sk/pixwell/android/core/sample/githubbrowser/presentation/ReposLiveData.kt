package sk.pixwell.android.core.sample.githubbrowser.presentation

import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.GetReposError
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel
import sk.pixwell.android.core.sample.githubbrowser.domain.usecase.repo.GetReposUseCase

object ReposLiveData : MutableLiveData<Either<GetReposError, List<RepoModel>>>() {
    private val getRepos = GetReposUseCase()

    init {
        value = null
    }

    override fun onActive() {
        getRepos.subscribe { value = it }
    }

    override fun onInactive() {
        getRepos.clear()
    }
}