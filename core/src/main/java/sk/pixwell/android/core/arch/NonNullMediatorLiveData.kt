package sk.pixwell.android.core.arch

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class NonNullMediatorLiveData<T> : MediatorLiveData<T>() {
    fun observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
        observe(owner, Observer { it?.let(observer) })
    }
}

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    return NonNullMediatorLiveData<T>().apply {
        addSource(this@nonNull) {
            it?.let { value = it }
        }
    }
}