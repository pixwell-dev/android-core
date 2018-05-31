package sk.pixwell.android.core.arch

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test

class NonNullMediatorLiveDataTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testNullValue() {
        val liveData = MutableLiveData<String>()
        val observer = mock<(String?) -> Unit>()
        val owner = mockLifecycleOwner()

        liveData.value = null

        liveData.nonNull()
            .observe(owner, observer)

        verify(observer, never()).invoke(null)
    }

    @Test
    fun testNonNullValue() {
        val liveData = MutableLiveData<String>()
        val observer = mock<(String) -> Unit>()
        val owner = mockLifecycleOwner()

        liveData.value = "value"

        liveData.nonNull()
            .observe(owner, observer)

        verify(observer).invoke("value")
    }

    private fun mockLifecycleOwner() = mock<LifecycleOwner> {
        on { lifecycle } doReturn LifecycleRegistry(mock).apply {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }
}