package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.github.ajalt.timberkt.e
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import sk.pixwell.android.core.arch.RetryRequest
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel

class ReposDataSource : PositionalDataSource<RepoModel>(), KoinComponent {

    private val repository by inject<RepoRepository>()
    val isLoading = MutableLiveData<Boolean>()
    val count = MutableLiveData<Int>()
    private val requests = mutableSetOf<RetryRequest<*, *>>()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<RepoModel>) {
        isLoading.postValue(true)
        val request =
        repository.getReposTop(params.requestedLoadSize).blockingForEach { result ->
            isLoading.postValue(false)
            result.fold({
                e { "getCurrentEvents error: $it" }

            }, {
                val count = it.totalCount
                this.count.postValue(count)
                callback.onResult(it.data.map {  RepoModel.fromDto(it) }, 0, count)
            })
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<RepoModel>) {
        repository.getCurrentRepos(params.startPosition / 10 + 1, params.loadSize).blockingForEach { result ->
            result.fold({
                e { "getCurrentEvents error: $it" }
            }, {
                callback.onResult(it.data.map { RepoModel.fromDto(it) })
            })
        }
    }

//    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<RepoModel>) {
//        isLoading.postValue(true)
//        repository.getReposTop(params.requestedLoadSize).blockingForEach { result ->
//            isLoading.postValue(false)
//            result.fold({
//                e { "getCurrentEvents error: $it" }
//            }, {
//                val count = it.totalCount
//                this.count.postValue(count)
//                callback.onResult(it.data.map { RepoModel.fromDto(it) }, 0, count)
//            })
//        }
//
//    }
//
//    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<RepoModel>) {
//        repository.getCurrentRepos(params.startPosition / 10 + 1, params.loadSize).blockingForEach { result ->
//            result.fold({
//                e { "getCurrentEvents error: $it" }
//            }, {
//                callback.onResult(it.data.map { RepoModel.fromDto(it) })
//            })
//        }
//    }
}