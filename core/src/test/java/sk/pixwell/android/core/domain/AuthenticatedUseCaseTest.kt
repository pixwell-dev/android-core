package sk.pixwell.android.core.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test

private const val TOKEN = "token"
private const val ERROR = "error"
private const val RESULT = "result"

class AuthenticatedUseCaseTest {
    @Test
    fun testOneShotNotAuthenticated() {
        val goToLogin = mock<() -> Unit>()

        val useCase = object : AuthenticatedUseCase<String, UseCase.Params, String>() {
            override fun buildAuth(): Observable<Either<AuthError, String>> {
                return Observable.just(AuthError.NotAuthenticatedError.left())
            }

            override fun goToLogin() {
                goToLogin()
            }

            override fun onAuthError(error: AuthError): String {
                return ERROR
            }

            override fun onAuthSuccess(token: String): Observable<String> {
                return Observable.just(RESULT)
            }

        }

        useCase.test(UseCase.Params()).apply {
            awaitCount(1)
            assertNotComplete()
            assertNoErrors()
            assertValue(ERROR)
        }
        verify(goToLogin, never()).invoke()
    }

    @Test
    fun testOneShotAuthenticated() {
        val goToLogin = mock<() -> Unit>()

        val useCase = object : AuthenticatedUseCase<String, UseCase.Params, String>() {
            override fun buildAuth(): Observable<Either<AuthError, String>> {
                return Observable.just(TOKEN.right())
            }

            override fun goToLogin() {
                goToLogin()
            }

            override fun onAuthError(error: AuthError): String {
                return ERROR
            }

            override fun onAuthSuccess(token: String): Observable<String> {
                return Observable.just(RESULT)
            }

        }

        assertEquals(listOf(RESULT), useCase.blockingIterable(UseCase.Params()).toList())
        verify(goToLogin, never()).invoke()
    }

    @Test
    fun testOneShotAutoLogin() {
        var isAuthenticated = false

        val useCase = object : AuthenticatedUseCase<String, UseCase.Params, String>() {
            override val autoLogin = true

            override fun buildAuth(): Observable<Either<AuthError, String>> {
                return if (isAuthenticated) {
                    Observable.just(TOKEN.right())
                } else {
                    Observable.just(AuthError.NotAuthenticatedError.left())
                }
            }

            override fun goToLogin() {
                isAuthenticated = true
                AuthenticatedUseCase.notifyAuthStatusChanged()
            }

            override fun onAuthError(error: AuthError): String {
                return ERROR
            }

            override fun onAuthSuccess(token: String): Observable<String> {
                return Observable.just(RESULT)
            }

        }

        assertEquals(listOf(ERROR, RESULT), useCase.blockingIterable(UseCase.Params()).toList())
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }
}