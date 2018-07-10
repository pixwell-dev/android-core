package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto

class RepoApiDataSource(retrofit: Retrofit) : RepoRemoteDataSource {
    private val repoApi = retrofit.create(RepoApi::class.java)

    override fun getRepos(since: Long): Either<GetReposError, List<RepoDto>> {
        return try {
            val response = repoApi.getRepos(since).execute()
            when (response.code()) {
                200 -> response.body()!!.right()
                else -> GetReposError.OtherError(response.message()).left()
            }
        } catch (e: Exception) {
            GetReposError.OtherError(e.message.toString()).left()
        }
    }

    private interface RepoApi {
        @GET("repositories")
        fun getRepos(@Query("since") since: Long): Call<List<RepoDto>>
    }
}