package pro.jeminay.tochka.auth

import android.app.Activity
import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pro.jeminay.tochka.api.FacebookApiService
import pro.jeminay.tochka.data.User
import javax.inject.Inject

class FacebookAuthStrategy @Inject constructor(
    private val apiService: FacebookApiService
) : AuthStrategy(), FacebookCallback<LoginResult> {
    companion object {
        private val PERMISSIONS = listOf("email", "public_profile")
    }

    private val callbackManager = CallbackManager.Factory.create()

    private val loginManager = LoginManager.getInstance().apply {
        registerCallback(callbackManager, this@FacebookAuthStrategy)
        logOut() // TODO Remove
    }

    override fun processAuth(activity: Activity) {
        loginManager.logInWithReadPermissions(activity,
            PERMISSIONS
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSuccess(result: LoginResult) {
        disposable = apiService.fetchSelf(result.accessToken.token, "first_name,last_name")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val avatarUrl = "http://graph.facebook.com/${it.id}/picture?type=large"
                requestSuccess(
                    User(
                        it.firstName,
                        it.lastName,
                        avatarUrl,
                        result.accessToken.token
                    )
                )
            }, {
                requestFailed(AuthException(it))
            })
    }

    override fun onError(error: FacebookException) {
        requestFailed(AuthException(error))
    }

    override fun onCancel() {
        // Do nothing
    }
}