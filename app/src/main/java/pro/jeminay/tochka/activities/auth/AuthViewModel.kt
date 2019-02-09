package pro.jeminay.tochka.activities.auth

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pro.jeminay.tochka.auth.AuthException
import pro.jeminay.tochka.auth.AuthListener
import pro.jeminay.tochka.auth.AuthStrategy
import pro.jeminay.tochka.consts.AuthTypes
import pro.jeminay.tochka.data.User
import pro.jeminay.tochka.auth.AuthManager
import pro.jeminay.tochka.data.UserRepo
import pro.jeminay.tochka.interfaces.ActivityResultReceiver
import pro.jeminay.tochka.utils.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Provider

class AuthViewModel @Inject constructor(
    private val authStrategyMap: Map<AuthTypes, @JvmSuppressWildcards Provider<AuthStrategy>>,
    private val userRepository: UserRepo,
    private val authManager: AuthManager
) : ViewModel(), ActivityResultReceiver, AuthListener {

    val authSuccessEvent = SingleLiveEvent<User>()

    val authFailedEvent = SingleLiveEvent<Unit>()

    private lateinit var currentAuthStrategy: AuthStrategy

    private val composite = CompositeDisposable()

    init {
        composite.addAll(
            userRepository.currentUser()
                .subscribe { finishAuth(it) }
        )
    }

    fun onAuthProcess(activity: Activity, type: AuthTypes) {
        val authStrategy = authStrategyMap[type]?.get()
            ?: throw Exception("AuthStrategy with type = $type not found")

        authStrategy.setAuthListener(this)
        authStrategy.processAuth(activity)

        currentAuthStrategy = authStrategy
    }

    private fun finishAuth(user: User) {
        authManager.setupUser(user)
        authSuccessEvent.call(user)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return currentAuthStrategy.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAuthSuccess(user: User) {
        composite.add(
            userRepository.createUser(user)
                .subscribe({ finishAuth(it) }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onAuthFailed(e: AuthException) {
        authFailedEvent.call()
    }

    override fun onCleared() {
        composite.clear()
    }
}