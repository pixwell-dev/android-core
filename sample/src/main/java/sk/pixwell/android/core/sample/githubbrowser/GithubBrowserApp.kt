package sk.pixwell.android.core.sample.githubbrowser

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.Timber.DebugTree
import org.koin.android.ext.android.startKoin
import sk.pixwell.android.core.sample.githubbrowser.di.appModule

class GithubBrowserApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin(this, listOf(appModule))
    }
}