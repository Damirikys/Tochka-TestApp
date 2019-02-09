package pro.jeminay.tochka.auth

import android.app.Activity
import android.content.Intent
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pro.jeminay.tochka.api.VKApiService
import pro.jeminay.tochka.data.User
import javax.inject.Inject

class VKAuthStrategy @Inject constructor(
    private val apiService: VKApiService
) : AuthStrategy(), VKAuthCallback {

    override fun processAuth(activity: Activity) {
        VK.login(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (data != null) {
            return VK.onActivityResult(requestCode, resultCode, data, this)
        }

        return false
    }

    override fun onLogin(token: VKAccessToken) {
        disposable = apiService.fetchSelf(token.accessToken, arrayOf("photo_400"))
            .subscribeOn(Schedulers.io())
            .map { it.response.first() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                requestSuccess(
                    User(
                        it.firstName,
                        it.lastName,
                        it.avatarUrl,
                        token.accessToken
                    )
                )
            }, {
                requestFailed(AuthException(it))
            })
    }

    override fun onLoginFailed(errorCode: Int) {
        requestFailed(AuthException("Failed VKAuth with errorCode = $errorCode"))
    }
}