package satya.app.healthcareapproomdb.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import satya.app.healthcareapproomdb.db.entities.UserAuthEntity

@Dao
interface UserAuthDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun userSignUp(userEntity: UserAuthEntity)

    @Query("SELECT * FROM user_auth WHERE email LIKE :email LIMIT 1")
    suspend fun checkUserExistence(email: String): UserAuthEntity?

    @Query("SELECT * FROM user_auth WHERE email LIKE :email AND password LIKE :password")
    suspend fun userLogin(email: String, password: String): UserAuthEntity?

}