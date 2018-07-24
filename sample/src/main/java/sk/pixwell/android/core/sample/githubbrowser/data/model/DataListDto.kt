package sk.pixwell.android.core.sample.githubbrowser.data.model

import com.google.gson.annotations.SerializedName

data class DataListDto<out T>(
        @SerializedName("data") val data: List<T>)