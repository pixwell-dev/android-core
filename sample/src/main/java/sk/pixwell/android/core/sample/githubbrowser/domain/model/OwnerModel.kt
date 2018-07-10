package sk.pixwell.android.core.sample.githubbrowser.domain.model

import sk.pixwell.android.core.sample.githubbrowser.data.model.OwnerDto

data class OwnerModel(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val url: String
) {

    companion object {
        fun fromDto(dto: OwnerDto) = OwnerModel(
            dto.id,
            dto.login,
            dto.avatarUrl,
            dto.url
        )
    }
}