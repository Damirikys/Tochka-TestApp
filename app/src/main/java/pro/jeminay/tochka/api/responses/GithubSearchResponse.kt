package pro.jeminay.tochka.api.responses

import com.google.gson.annotations.SerializedName

data class GithubSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    val items: List<GithubUser>
)