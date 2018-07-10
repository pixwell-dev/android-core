package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import arrow.core.Either
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto

interface RepoRemoteDataSource {
    fun getRepos(since: Long): Either<GetReposError, List<RepoDto>>
}