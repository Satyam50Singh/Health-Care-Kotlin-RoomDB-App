package satya.app.healthcareapproomdb.repositories

import androidx.lifecycle.LiveData
import satya.app.healthcareapproomdb.db.dao.UserAuthDao
import satya.app.healthcareapproomdb.db.entities.UserAuthEntity

class UserAuthRepository(
    private val userAuthDao: UserAuthDao
) {

    fun checkUserExistence(email: String): LiveData<UserAuthEntity> =
        userAuthDao.checkUserExistence(email)

    suspend fun userSignUp(userAuthEntity: UserAuthEntity): Long =
        userAuthDao.userSignUp(userAuthEntity)

    fun userLogin(email: String, password: String): LiveData<UserAuthEntity> =
        userAuthDao.userLogin(email, password)

    fun changePassword(email: String, password: String, newPassword: String): Int =
        userAuthDao.changePassword(email, password, newPassword)

}