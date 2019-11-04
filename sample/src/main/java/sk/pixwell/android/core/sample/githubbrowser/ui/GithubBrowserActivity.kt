package sk.pixwell.android.core.sample.githubbrowser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.e
import kotlinx.android.synthetic.main.activity_githubbrowser.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import sk.pixwell.android.core.arch.obtainViewModel
import sk.pixwell.android.core.sample.githubbrowser.R
import sk.pixwell.android.core.sample.githubbrowser.presentation.ReposLiveData
import sk.pixwell.android.core.utils.TimeAgo

class GithubBrowserActivity : AppCompatActivity() {
    private val viewModel by lazy { obtainViewModel<GithubBrowserViewModel>() }
    private val adapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_githubbrowser)

        TimeAgo().locale(this).getTimeAgo(LocalDateTime.ofInstant(ZonedDateTime.parse("2018-09-28T00:52:50+00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[XXX][X]")).toInstant(), ZoneId.systemDefault()))

        reposRecycler.adapter = adapter

        ReposLiveData.observe(this, Observer {
            if (it == null) {
                loader.show()
            } else {
                it.fold({
                    e { "getReposError: $it" }
                }, {
                    adapter.items = it
                })
                loader.hide()
            }
        })
    }
}