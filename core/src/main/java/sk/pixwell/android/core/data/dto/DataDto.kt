package sk.pixwell.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class DataDto<T>(@SerializedName("data") val data: T)