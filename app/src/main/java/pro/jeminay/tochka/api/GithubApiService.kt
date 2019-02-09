package pro.jeminay.tochka.api

import io.reactivex.Single
import pro.jeminay.tochka.api.responses.GithubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("/search/users?sort=followers")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): Single<GithubSearchResponse>
}