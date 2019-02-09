package pro.jeminay.tochka.api.responses

import com.google.gson.annotations.SerializedName

data class VKUser(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_400") val avatarUrl: String
)