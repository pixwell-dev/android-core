package sk.pixwell.android.core.sample.githubbrowser.data.model

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("url") val url: String
)