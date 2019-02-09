package pro.jeminay.tochka.api.responses

import com.google.gson.annotations.SerializedName

data class FBUser(
    val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)