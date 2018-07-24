package sk.pixwell.android.core.arch

import arrow.core.left
import arrow.core.right
import org.junit.Before
import org.junit.Test

private const val SUCCESS = "success"
private const val ERROR = "error"

class RetryRequestTest {
    private var failedCounter = 0

    @Before
    fun setUp() {
        failedCounter = 0
    }

    @Test
    fun testSuccess() {
        val request = { SUCCESS.right() }
        RetryRequest(request).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(SUCCESS.right())
        }
    }

    @Test
    fun testRetry() {
        val request = {
            if (failedCounter == 3) {
                SUCCESS.right()
            } else {
                failedCounter++
                ERROR.left()
            }
        }
        RetryRequest(request).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(ERROR.left(), ERROR.left(), ERROR.left(), SUCCESS.right())
        }
    }
}