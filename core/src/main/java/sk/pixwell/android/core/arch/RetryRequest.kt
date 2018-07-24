package sk.pixwell.android.core.arch

import arrow.core.Either
import com.github.ajalt.timberkt.e
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class RetryRequest<E, S>(private val request: () -> Either<E, S>, maxRetryCount: Int = 0) {
    private var _interval: Long = 1
    val interval: Long get() = _interval
    private val subject: Subject<Long> = BehaviorSubject.createDefault(interval)
    val observable: Observable<Either<E, S>> = subject.switchMap { Observable.timer(interval, TimeUnit.SECONDS) }
            .map { request.invoke() }
            .doOnNext { result ->
                result.fold({
                    _interval *= 2
                    subject.onNext(interval)
                }, {
                    subject.onComplete()
                })
            }
            .take(maxRetryCount + 1L)

    fun subscribe(result: (Either<E, S>) -> Unit): Disposable = observable.subscribe(result) { e { "$it" } }

    fun test(): TestObserver<Either<E, S>> = observable.test()
}