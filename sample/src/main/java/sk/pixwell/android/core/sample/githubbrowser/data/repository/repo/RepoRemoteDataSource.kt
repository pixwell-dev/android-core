package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import arrow.core.Either
import sk.pixwell.android.core.data.dto.DataListDto
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto

interface RepoRemoteDataSource {
    fun getReposTop(per_page: Int): Either<GetReposError, DataListDto<RepoDto>>
    fun getReposCurrent(page: Int, per_page: Int): Either<GetReposError, DataListDto<RepoDto>>
}