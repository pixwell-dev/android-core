package sk.pixwell.android.core.sample.githubbrowser.domain.model

import sk.pixwell.android.core.domain.model.UiModel
import sk.pixwell.android.core.sample.githubbrowser.data.model.RepoDto

data class RepoModel(
    override val viewId: String,
    val owner: OwnerModel,
    val fullName: String,
    val description: String?,
    val url: String
) : UiModel {

    companion object {
        fun fromDto(dto: RepoDto) = RepoModel(
            dto.id.toString(),
            OwnerModel.fromDto(dto.owner),
            dto.fullName,
            dto.description,
            dto.url
        )
    }
}