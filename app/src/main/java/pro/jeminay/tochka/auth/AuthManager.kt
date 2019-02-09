package pro.jeminay.tochka.auth

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pro.jeminay.tochka.data.User
import pro.jeminay.tochka.data.UserDao
import java.lang.Exception

class AuthManager(private val userDao: UserDao) {

    private var currentUser: User? = null

    fun requireUser(): User = currentUser ?: throw Exception("User not found")

    fun user(): User? = currentUser

    fun setupUser(user: User) {
        currentUser = user
    }

    fun subscribe(): Observable<User> = userDao.currentUserObservable()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
}