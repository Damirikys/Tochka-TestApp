package pro.jeminay.tochka.api

import io.reactivex.Single
import pro.jeminay.tochka.api.responses.FBUser
import retrofit2.http.GET
import retrofit2.http.Query

interface FacebookApiService {

    @GET("/me")
    fun fetchSelf(
        @Query("access_token") token: String,
        @Query("fields") fields: String
    ): Single<FBUser>
}