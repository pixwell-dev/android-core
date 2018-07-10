package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

sealed class GetReposError {
    data class OtherError(val msg: String) : GetReposError()
}