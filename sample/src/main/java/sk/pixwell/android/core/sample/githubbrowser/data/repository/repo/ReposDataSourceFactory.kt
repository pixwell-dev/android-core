package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto
import sk.pixwell.android.core.sample.githubbrowser.domain.model.RepoModel

class ReposDataSourceFactory : DataSource.Factory<Int, RepoModel>() {
    val sourceLiveData = MutableLiveData<ReposDataSource>()

    override fun create(): DataSource<Int, RepoModel> {
        val source = ReposDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}