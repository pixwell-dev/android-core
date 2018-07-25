package sk.pixwell.android.core.data.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.right
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

abstract class Repository {
    companion object {
        @JvmStatic
        fun <E, D> buildSource(
            cachePolicy: CachePolicy,
            local: (() -> Option<D>)? = null,
            remote: (() -> Either<E, D>)? = null,
            onNext: ((D) -> Unit)? = null
        ): Observable<Either<E, D>> {
            val localObservable: Observable<Either<E, D>> = Observable.create { emitter ->
                local?.invoke()?.map { emitter.onNext(it.right()) }
                emitter.onComplete()
            }

            val remoteObservable =
                if (remote == null) Observable.empty() else Observable.fromCallable(remote)
                    .doOnNext { it.map { onNext?.invoke(it) } }

            val sources = when (cachePolicy) {
                CachePolicy.LocalOnly -> listOf(localObservable)
                CachePolicy.LocalFirst -> listOf(localObservable, remoteObservable)
                CachePolicy.LocalBefore -> listOf(localObservable, remoteObservable)
                CachePolicy.NetworkOnly -> listOf(remoteObservable)
                CachePolicy.NetworkFirst -> listOf(remoteObservable, localObservable)
            }

            val source = Observable.concat(sources)
                .subscribeOn(Schedulers.io())

            return when (cachePolicy) {
                CachePolicy.LocalFirst -> {
                    source.filter { it.isRight() }
                        .switchIfEmpty(remoteObservable)
                }
                CachePolicy.NetworkFirst, CachePolicy.LocalBefore -> {
                    source.takeUntil { it.isRight() }
                        .filter { it.isRight() }
                        .switchIfEmpty(remoteObservable)
                }
                else -> source
            }
        }
    }

    sealed class CachePolicy {
        object LocalOnly : CachePolicy()
        object LocalFirst : CachePolicy()
        object LocalBefore : CachePolicy()
        object NetworkOnly : CachePolicy()
        object NetworkFirst : CachePolicy()
    }
}