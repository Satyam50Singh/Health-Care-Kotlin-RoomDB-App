package satya.app.healthcareapproomdb.repositories;

import androidx.lifecycle.LiveData
import satya.app.healthcareapproomdb.db.dao.HealthArticleDao
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity

class HealthArticleRepository(
    private val healthArticleDao: HealthArticleDao
) {

    fun getAllHealthArticles(): LiveData<List<HealthArticleEntity>> =
        healthArticleDao.getAllArticles()

    suspend fun insertHealthArticle(healthArticleEntity: HealthArticleEntity): Long =
        healthArticleDao.addArticle(healthArticleEntity)


    suspend fun removeHealthArticle(healthArticleEntity: HealthArticleEntity) {
        healthArticleDao.deleteArticle(healthArticleEntity)
    }

    suspend fun removeAllHealthArticles() = healthArticleDao.deleteAllArticle()
}
