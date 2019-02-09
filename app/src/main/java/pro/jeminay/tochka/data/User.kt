package pro.jeminay.tochka.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var firstName: String,
    var lastName: String,
    var avatarUrl: String,
    var token: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}