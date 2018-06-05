package sk.pixwell.android.core.domain

import android.util.Log
import com.github.ajalt.timberkt.e
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

typealias UseCaseInterceptor = (Any) -> Unit

abstract class UseCase<P : UseCase.Params, T : Any> {
    protected lateinit var params: P
    private var disposable: Disposable? = null

    abstract fun build(): Observable<T>

    fun subscribe(
        params: P,
        onNext: (result: T) -> Unit,
        onComplete: () -> Unit
    ) {
        clear()
        this.params = params
        disposable = build()
            .map { it.also { interceptors.forEach { interceptor -> interceptor(it) } } }
            .observeOn(AndroidSchedulers.mainThread())
            .publish()  // Prevent unsubscribing upstream when the downstream unsubscribes
            .autoConnect()
            .subscribe(onNext, ::handleError, onComplete)
    }

    fun subscribe(
        params: P,
        onNext: (result: T) -> Unit
    ) = subscribe(params, onNext, {})

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