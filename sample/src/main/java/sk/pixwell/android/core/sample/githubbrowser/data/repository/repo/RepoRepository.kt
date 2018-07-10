package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import sk.pixwell.android.core.data.repository.Repository

class RepoRepository(private val remote: RepoRemoteDataSource) : Repository() {
    fun getRepos(since: Long) = buildSource(
        CachePolicy.NetworkOnly,
        remote = { remote.getRepos(since) }
    )
}