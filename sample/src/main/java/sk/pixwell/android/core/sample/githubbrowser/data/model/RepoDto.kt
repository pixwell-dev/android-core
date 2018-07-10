package sk.pixwell.android.core.sample.githubbrowser.data.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("owner") val owner: OwnerDto,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String
)