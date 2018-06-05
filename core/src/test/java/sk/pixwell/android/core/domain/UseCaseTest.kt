package sk.pixwell.android.core.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

private const val VALUE = "value"

class UseCaseTest {
    @Before
    fun setUp() {
        UseCase.clearInterceptors()
    }

    @Test
    fun testSubscribe() {
        val interceptor = mock<(Any) -> Unit>()
        UseCase.addInterceptor(interceptor)

        val useCase = object : UseCase<UseCase.Params, String>() {
            override fun build(): Observable<String> {
                return Observable.just(VALUE)
            }
        }

        useCase.test(UseCase.Params()).apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(VALUE)
        }

        verify(interceptor).invoke(VALUE)
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            UseCase.clearInterceptors()
        }
    }
}