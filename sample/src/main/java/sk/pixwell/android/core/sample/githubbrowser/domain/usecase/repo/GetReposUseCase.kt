package sk.pixwell.android.core.sample.githubbrowser.domain.usecase.repo

import arrow.core.Either
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import sk.pixwell.android.core.domain.usecase.UseCase
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.GetReposError
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.RepoRepository
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel

class GetReposUseCase : UseCase<UseCase.Params, Either<GetReposError, List<RepoModel>>>(),
    KoinComponent {

    private val repoRepository by inject<RepoRepository>()

    override fun build(): Observable<Either<GetReposError, List<RepoModel>>> {
        return repoRepository.getRepos(0)
            .map { it.map { it.map { RepoModel.fromDto(it) } } }
    }

    fun subscribe(onNextConsumer: (result: Either<GetReposError, List<RepoModel>>) -> Unit) {
        super.subscribe(Params(), onNextConsumer)
    }
}