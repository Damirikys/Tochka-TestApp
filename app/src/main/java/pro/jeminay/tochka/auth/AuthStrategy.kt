package pro.jeminay.tochka.auth

import android.app.Activity
import android.content.Intent
import io.reactivex.disposables.Disposable
import pro.jeminay.tochka.data.User
import java.lang.ref.WeakReference

abstract class AuthStrategy {

    private var authListenerRef: WeakReference<AuthListener>? = null

    protected var disposable: Disposable? = null

    abstract fun processAuth(activity: Activity)

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean

    fun setAuthListener(listener: AuthListener) {
        authListenerRef = WeakReference(listener)
    }

    fun requestSuccess(user: User) {
        authListenerRef?.get()?.onAuthSuccess(user)
        reset()
    }

    fun requestFailed(e: AuthException) {
        authListenerRef?.get()?.onAuthFailed(e)
        reset()
    }

    private fun reset() {
        authListenerRef?.clear()
        authListenerRef = null
        disposable?.dispose()
        disposable = null
    }
}