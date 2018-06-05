package sk.pixwell.android.core.domain

import android.util.Log
import com.github.ajalt.timberkt.e
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver

typealias UseCaseInterceptor = (Any) -> Unit

abstract class UseCase<P : UseCase.Params, T : Any> {
    protected lateinit var params: P
    private var disposable: Disposable? = null

    abstract fun build(): Observable<T>

    private fun prepareSubscribe(params: P): Observable<T> {
        clear()
        this.params = params
        return build()
            .map { it.also { interceptors.forEach { interceptor -> interceptor(it) } } }
            .observeOn(AndroidSchedulers.mainThread())
            .publish()  // Prevent unsubscribing upstream when the downstream unsubscribes
            .autoConnect()
    }

    fun subscribe(
        params: P,
        onNext: (result: T) -> Unit,
        onComplete: () -> Unit
    ): Disposable = prepareSubscribe(params).subscribe(onNext, ::handleError, onComplete)

    fun subscribe(
        params: P,
        onNext: (result: T) -> Unit
    ) = subscribe(params, onNext, {})

    fun test(
        params: P
    ): TestObserver<T> = prepareSubscribe(params).test()

    fun blockingIterable(
        params: P
    ): MutableIterable<T> = prepareSubscribe(params).blockingIterable()

    protected fun handleError(t: Throwable) {
        e { Log.getStackTraceString(t) }
    }

    open fun clear() {
        disposable?.dispose()
    }

    open class Params

    companion object {
        private val interceptors = mutableSetOf<UseCaseInterceptor>()

        fun addInterceptor(interceptor: UseCaseInterceptor) = interceptors.add(interceptor)

        fun removeInterceptor(interceptor: UseCaseInterceptor) = interceptors.remove(interceptor)

        fun clearInterceptors() = interceptors.clear()
    }
}