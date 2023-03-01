package satya.app.healthcareapproomdb.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_auth", indices = [Index(value = ["email"], unique = true)])
data class UserAuthEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int?,
    val username: String,
    val email: String,
    val password: String
)