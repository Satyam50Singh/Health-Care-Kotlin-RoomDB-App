package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity
import satya.app.healthcareapproomdb.repositories.HealthArticleRepository

class HealthArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HealthArticleRepository

    init {
        val healthArticleDao =
            AppDatabase.getDatabase(application.applicationContext).healthArticleDao()
        repository = HealthArticleRepository(healthArticleDao)
    }

    fun getAllArticles(): LiveData<List<HealthArticleEntity>> =
        repository.getAllHealthArticles()

    fun addArticle(healthArticleEntity: HealthArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertHealthArticle(healthArticleEntity)
            Log.e("TAG", "healthArticleEntity - insert call: $result ")
        }
    }
}