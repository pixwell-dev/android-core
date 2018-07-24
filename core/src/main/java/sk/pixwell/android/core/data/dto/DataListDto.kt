package sk.pixwell.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class DataListDto<out T>(
        @SerializedName("items") val data: List<T>,
        @SerializedName("total_count") val totalCount: Int)