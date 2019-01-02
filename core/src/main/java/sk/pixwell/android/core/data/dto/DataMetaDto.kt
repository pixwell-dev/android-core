package sk.pixwell.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class DataMetaDto(
    @SerializedName("pagination") val pagination: PaginationDto?
)