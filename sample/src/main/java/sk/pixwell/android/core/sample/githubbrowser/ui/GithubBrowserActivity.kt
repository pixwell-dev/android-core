package sk.pixwell.android.core.sample.githubbrowser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.e
import kotlinx.android.synthetic.main.activity_githubbrowser.*
import sk.pixwell.android.core.arch.obtainViewModel
import sk.pixwell.android.core.sample.githubbrowser.R
import sk.pixwell.android.core.sample.githubbrowser.presentation.ReposLiveData

class GithubBrowserActivity : AppCompatActivity() {
    private val viewModel by lazy { obtainViewModel<GithubBrowserViewModel>() }
    private val adapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_githubbrowser)

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