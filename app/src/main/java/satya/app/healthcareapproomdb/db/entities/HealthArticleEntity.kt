package satya.app.healthcareapproomdb.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "health_article")
data class HealthArticleEntity(
    @PrimaryKey(autoGenerate = true) val article_id: Int?,
    val title: String,
    val uploadedOn: String,
    val article: String
) : Parcelable