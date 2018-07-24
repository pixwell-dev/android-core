package sk.pixwell.android.core.data.repository

import arrow.core.None
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import org.junit.Test

private const val LOCAL = "local"
private const val REMOTE = "remote"

class RepositoryTest {
    @Test
    fun testBuildSourceLocalOnlySuccess() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.LocalOnly,
            local = { LOCAL.toOption() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(LOCAL.right())
        }
    }

    @Test
    fun testBuildSourceLocalOnlyError() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.LocalOnly,
            local = { None }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertValueCount(0)
        }
    }

    @Test
    fun testBuildSourceNetworkOnlySuccess() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.NetworkOnly,
            remote = { REMOTE.right() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.right())
        }
    }

    @Test
    fun testBuildSourceNetworkOnlyError() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.NetworkOnly,
            remote = { REMOTE.left() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.left())
        }
    }

    @Test
    fun testBuildSourceLocalFirstErrorError() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.LocalFirst,
            local = { None },
            remote = { REMOTE.left() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.left())
        }
    }

    @Test
    fun testBuildSourceLocalFirstErrorSuccess() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.LocalFirst,
            local = { None },
            remote = { REMOTE.right() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.right())
        }
    }

    @Test
    fun testBuildSourceLocalFirstSuccessError() {
        Repository.buildSource(
            Repository.CachePolicy.LocalFirst,
            local = { LOCAL.toOption() },
            remote = { REMOTE.left() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(LOCAL.right())
        }
    }

    @Test
    fun testBuildSourceLocalFirstSuccessSuccess() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.LocalFirst,
            local = { LOCAL.toOption() },
            remote = { REMOTE.right() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(LOCAL.right(), REMOTE.right())
        }
    }

    @Test
    fun testBuildSourceNetworkFirstErrorError() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.NetworkFirst,
            local = { None },
            remote = { REMOTE.left() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.left())
        }
    }

    @Test
    fun testBuildSourceNetworkFirstErrorSuccess() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.NetworkFirst,
            local = { None },
            remote = { REMOTE.right() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.right())
        }
    }

    @Test
    fun testBuildSourceNetworkFirstSuccessError() {
        Repository.buildSource(
            Repository.CachePolicy.NetworkFirst,
            local = { LOCAL.toOption() },
            remote = { REMOTE.left() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(LOCAL.right())
        }
    }

    @Test
    fun testBuildSourceNetworkFirstSuccessSuccess() {
        Repository.buildSource<String, String>(
            Repository.CachePolicy.NetworkFirst,
            local = { LOCAL.toOption() },
            remote = { REMOTE.right() }
        ).test().apply {
            awaitTerminalEvent()
            assertComplete()
            assertNoErrors()
            assertResult(REMOTE.right())
        }
    }
}