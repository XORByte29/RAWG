package tech.noope.common.domain.data

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<GameDataModel> = listOf(),
)