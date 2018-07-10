package sk.pixwell.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class DataListDto<T>(@SerializedName("data") val data: List<T>)