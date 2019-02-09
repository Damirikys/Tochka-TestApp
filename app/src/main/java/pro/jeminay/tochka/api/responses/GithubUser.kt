package pro.jeminay.tochka.api.responses

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)