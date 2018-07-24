package sk.pixwell.android.core.sample.githubbrowser.data.repository.repo

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import sk.pixwell.android.core.data.dto.DataListDto
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto

class RepoApiDataSource(retrofit: Retrofit) : RepoRemoteDataSource {
    private val repoApi = retrofit.create(RepoApi::class.java)

    override fun getReposTop(per_page: Int): Either<GetReposError, DataListDto<RepoDto>> {
        return try {
            val response = repoApi.getRepos(1, per_page).execute()
            when (response.code()) {
                200 -> response.body()!!.right()
                else -> GetReposError.OtherError(response.message()).left()
            }
        } catch (e: Exception) {
            GetReposError.OtherError(e.message.toString()).left()
        }
    }

    override fun getReposCurrent(page: Int, per_page: Int): Either<GetReposError, DataListDto<RepoDto>> {
        return try {
            val response = repoApi.getRepos(page, per_page).execute()
            when (response.code()) {
                200 -> response.body()!!.right()
                else -> GetReposError.OtherError(response.message()).left()
            }
        } catch (e: Exception) {
            GetReposError.OtherError(e.message.toString()).left()
        }
    }

    private interface RepoApi {
        @GET("search/repositories?q=\"a\"")
        fun getRepos(@Query("page") page: Int,
                     @Query("per_page") per_page: Int): Call<DataListDto<RepoDto>>
    }
}