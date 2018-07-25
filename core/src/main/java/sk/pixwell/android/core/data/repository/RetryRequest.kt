package sk.pixwell.android.core.data.repository

import arrow.core.Either
import com.github.ajalt.timberkt.d
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class RetryRequest<E, D>(request: () -> Either<E, D>, maxRetryCount: Int = 0) {
    private var _interval: Long = 1
    val interval: Long get() = _interval
    private val subject: Subject<Long> = BehaviorSubject.createDefault(interval)
    val observable: Observable<Either<E, D>> =
        subject.switchMap { Observable.timer(interval, TimeUnit.SECONDS) }
            .map { request.invoke() }
            .doOnNext { it.fold(::handleRequestError, ::handleRequestSuccess) }
            .take(maxRetryCount + 1L)

    private fun handleRequestError(error: E) {
        d { "requestError: $error" }
        _interval *= 2
        subject.onNext(interval)
    }

    private fun handleRequestSuccess(data: D) {
        d { "requestSuccess: $data" }
        subject.onComplete()
    }

    fun test(): TestObserver<Either<E, D>> = observable.test()
}