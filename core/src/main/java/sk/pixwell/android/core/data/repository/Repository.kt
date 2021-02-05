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
        /**
         * Tries to use only local data source
         */
        object LocalOnly : CachePolicy()

        /**
         * Tries to use local first and then remote. If both observables result in success, it
         * returns both results.
         */
        object LocalFirst : CachePolicy()

        /**
         * Tries to use local data source first and returns value if and only if it result in success.
         * Difference between LocalFirst and LocalBefore is that if remote and local result in success:
         *
         * * LocalFirst - returns both observables
         * * LocalBefore - returns only local observable
         *
         */
        object LocalBefore : CachePolicy()

        /**
         * Tries to use only remote data source
         */
        object NetworkOnly : CachePolicy()

        /**
         * Tries to use only remote data source first, and only if it fails, returns local
         */
        object NetworkFirst : CachePolicy()
    }
}
