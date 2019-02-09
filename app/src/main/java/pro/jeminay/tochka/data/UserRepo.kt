package pro.jeminay.tochka.data

import io.reactivex.Maybe
import io.reactivex.Single

interface UserRepo {

    fun currentUser(): Maybe<User>

    fun createUser(user: User): Single<User>

    fun deleteUser(): Single<Unit>
}