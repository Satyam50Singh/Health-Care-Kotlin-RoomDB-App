package satya.app.healthcareapproomdb.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import satya.app.healthcareapproomdb.db.entities.UserAuthEntity

@Dao
interface UserAuthDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun userSignUp(userEntity: UserAuthEntity): Long

    @Query("SELECT * FROM user_auth WHERE email LIKE :email LIMIT 1")
    fun checkUserExistence(email: String): LiveData<UserAuthEntity>

    @Query("SELECT * FROM user_auth WHERE email LIKE :email AND password LIKE :password")
    fun userLogin(email: String, password: String): LiveData<UserAuthEntity>

}