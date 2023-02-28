package satya.app.healthcareapproomdb.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HealthArticleModel(
    val title: String,
    val uploadedOn: String,
    val article: String
) : Parcelable
