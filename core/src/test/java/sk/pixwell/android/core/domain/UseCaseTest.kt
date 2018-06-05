package sk.pixwell.android.core.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*

class UseCaseTest {
    @Before
    fun setUp() {
        UseCase.clearInterceptors()
    }

    @Test
    fun testInterceptor() {
        val interceptor = mock<(Any) -> Unit>()
        UseCase.addInterceptor(interceptor)

        val useCase = mock<UseCase<UseCase.Params, String>> {
            on { build() } doReturn Observable.just("value")
        }

        useCase.subscribe(UseCase.Params()) {}

        verify(interceptor).invoke("value")
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