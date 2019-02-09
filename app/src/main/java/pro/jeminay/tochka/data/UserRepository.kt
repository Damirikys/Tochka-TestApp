package pro.jeminay.tochka.data

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val userDao: UserDao) : UserRepo {

    override fun currentUser(): Maybe<User> = userDao.currentUser()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())

    override fun createUser(user: User): Single<User> = Completable.fromAction { userDao.create(user) }
        .subscribeOn(Schedulers.computation())
        .andThen(userDao.currentUser())
        .toSingle()
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteUser(): Single<Unit> = userDao.currentUser()
        .subscribeOn(Schedulers.computation())
        .toSingle()
        .map { userDao.delete(it) }
        .observeOn(AndroidSchedulers.mainThread())
}