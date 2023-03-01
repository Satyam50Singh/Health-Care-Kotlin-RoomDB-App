package satya.app.healthcareapproomdb.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_article")
data class HealthArticleEntity(
    @PrimaryKey(autoGenerate = true) val article_id: Int?,
    val title: String,
    val uploadedOn: String,
    val article: String
)