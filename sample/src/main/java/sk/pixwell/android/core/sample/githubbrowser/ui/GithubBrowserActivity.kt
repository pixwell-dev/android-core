package sk.pixwell.android.core.sample.githubbrowser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.activity_githubbrowser.*
import sk.pixwell.android.core.arch.obtainViewModel
import sk.pixwell.android.core.sample.githubbrowser.R
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel

class GithubBrowserActivity : AppCompatActivity() {
    private val viewModel by lazy { obtainViewModel<GithubBrowserViewModel>() }
    private val adapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_githubbrowser)

        reposRecycler.adapter = adapter


        viewModel.isEmpty.observe(this, isEmptyObserver)
        viewModel.loading.observe(this, loadingObserver)
        viewModel.repos.observe(this, reposObserver)
    }

    private val isEmptyObserver = Observer<Boolean> {
        //todo is Empty set
    }

    private val loadingObserver = Observer<Boolean> {
        if (it == false) {
            //todo swipeRefresh
            //swipeRefresh.isRefreshing = it
        }
    }

    private val reposObserver = Observer<PagedList<RepoModel>> {
        adapter.submitList(it)
    }
}