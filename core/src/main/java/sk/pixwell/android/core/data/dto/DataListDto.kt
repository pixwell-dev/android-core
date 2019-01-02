package sk.pixwell.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class DataListDto<out T>(
        @SerializedName("data") val data: List<T>,
        @SerializedName("meta") val meta: DataMetaDto?
)