package pro.jeminay.tochka.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun currentUser(): Maybe<User>

    @Query("SELECT * FROM user LIMIT 1")
    fun currentUserObservable(): Observable<User>

    @Insert
    fun create(user: User)

    @Delete
    fun delete(user: User)
}