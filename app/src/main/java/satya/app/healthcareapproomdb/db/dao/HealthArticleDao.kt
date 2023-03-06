package satya.app.healthcareapproomdb.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity

@Dao
interface HealthArticleDao {
    @Query("SELECT * FROM health_article")
    fun getAllArticles(): LiveData<List<HealthArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArticle(healthArticleEntity: HealthArticleEntity): Long

    @Delete
    suspend fun deleteArticle(healthArticleEntity: HealthArticleEntity)

    @Query("DELETE FROM health_article")
    suspend fun deleteAllArticle()
}