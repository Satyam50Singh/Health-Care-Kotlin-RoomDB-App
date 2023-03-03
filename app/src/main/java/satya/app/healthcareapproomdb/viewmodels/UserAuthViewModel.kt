package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.UserAuthEntity
import satya.app.healthcareapproomdb.repositories.UserAuthRepository

class UserAuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserAuthRepository

    init {
        val userAuthDao = AppDatabase.getDatabase(application.applicationContext).userAuthDao()
        repository = UserAuthRepository(userAuthDao)
    }

    fun userSignUp(userAuthEntity: UserAuthEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.userSignUp(userAuthEntity)
        }
    }

    fun checkUserExistence(email: String): LiveData<UserAuthEntity> =
        repository.checkUserExistence(email)

    fun userLogIn(email: String, password: String) = repository.userLogin(email, password)
}