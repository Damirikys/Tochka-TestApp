package pro.jeminay.tochka.auth

import pro.jeminay.tochka.data.User

interface AuthListener {

    fun onAuthSuccess(user: User)

    fun onAuthFailed(e: AuthException)
}