package pro.jeminay.tochka.auth

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import pro.jeminay.tochka.data.User
import javax.inject.Inject

class GoogleAuthStrategy @Inject constructor() : AuthStrategy() {
    companion object {
        const val RC_SIGN_IN = 6038
    }

    private val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestProfile()
        .build()

    override fun processAuth(activity: Activity) {
        val googleClient = GoogleSignIn.getClient(activity, options)
        activity.startActivityForResult(googleClient.signInIntent,
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    requestSuccess(
                        User(
                            account.givenName!!,
                            account.familyName!!,
                            account.photoUrl!!.toString(),
                            account.zab()
                        )
                    )
                } else {
                    requestFailed(AuthException("Google account is null"))
                }
            } catch (e: ApiException) {
                requestFailed(AuthException(e))
            }
        }

        return false
    }
}