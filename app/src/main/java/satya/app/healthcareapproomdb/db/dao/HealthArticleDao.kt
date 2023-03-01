package satya.app.healthcareapproomdb.db.dao

import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity

@Dao
interface HealthArticleDao {
    @Query("SELECT * FROM health_article")
    fun getAllArticles(): List<HealthArticleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArticle(healthArticleEntity: HealthArticleEntity)

    @Delete
    suspend fun deleteArticle(healthArticleEntity: HealthArticleEntity)

    @Query("DELETE FROM health_article")
    suspend fun deleteAllArticle()
}