package com.picpay.desafio.android.data.remote

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("img") val image: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?
)