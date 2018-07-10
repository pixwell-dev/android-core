package sk.pixwell.android.core.sample.githubbrowser.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.RepoApiDataSource
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.RepoRemoteDataSource
import sk.pixwell.android.core.sample.githubbrowser.data.repository.repo.RepoRepository
import sk.pixwell.android.core.sample.githubbrowser.domain.usecase.repo.GetReposUseCase

val appModule = applicationContext {
    bean {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttp = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()
    }

    bean { RepoApiDataSource(get()) as RepoRemoteDataSource }
    bean { RepoRepository(get()) }
}