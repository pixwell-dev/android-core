package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import sk.pixwell.android.core.data.repository.Repository

class RepoRepository(
        private val remote: RepoRemoteDataSource) : Repository() {

    fun getReposTop(perPage: Int) = buildSource(
        CachePolicy.NetworkOnly,
        remote = { remote.getReposTop(perPage) }
    )

    fun getCurrentRepos(page: Int, perPage: Int) = buildSource(
        CachePolicy.NetworkOnly,
        remote = { remote.getReposCurrent(page, perPage) }
    )
}