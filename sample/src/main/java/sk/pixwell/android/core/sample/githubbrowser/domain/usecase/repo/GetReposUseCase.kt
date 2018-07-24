package sk.pixwell.android.core.sample.githubbrowser.domain.usecase.repo

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import arrow.core.Either
import arrow.core.right
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import sk.pixwell.android.core.domain.usecase.UseCase
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto
import sk.pixwell.android.core.sample.githubbrowser.data.repository.Listing
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.GetReposError
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.RepoRepository
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.ReposDataSourceFactory
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel

class GetReposUseCase : UseCase<UseCase.Params, Either<GetReposError, Listing<RepoModel>>>(),
    KoinComponent {

    //private val repoRepository by inject<RepoRepository>()

    override fun build(): Observable<Either<GetReposError, Listing<RepoModel>>> {
        val factory = ReposDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(true)
                .build()
        val livePagedList = LivePagedListBuilder(factory, config)
                .build()
        return Observable.just(Listing(
                pagedList = livePagedList,
                count = switchMap(factory.sourceLiveData) { it.count },
                isLoading = switchMap(factory.sourceLiveData) { it.isLoading },
                refresh = { factory.sourceLiveData.value?.invalidate() }
        ).right())
    }

    fun subscribe(onNextConsumer: (result: Either<GetReposError, Listing<RepoModel>>) -> Unit) {
        super.subscribe(Params(), onNextConsumer)
    }
}