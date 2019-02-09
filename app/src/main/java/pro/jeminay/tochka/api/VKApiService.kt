package pro.jeminay.tochka.api

import io.reactivex.Single
import pro.jeminay.tochka.api.responses.VKResponse
import pro.jeminay.tochka.api.responses.VKUser
import retrofit2.http.GET
import retrofit2.http.Query

interface VKApiService {

    @GET("/method/users.get")
    fun fetchSelf(
        @Query("access_token") token: String,
        @Query("fields") fields: Array<String>,
        @Query("v") version: String = "5.92"
    ): Single<VKResponse<Array<VKUser>>>
}