package satya.app.healthcareapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AmbulanceModel(
    val ambulanceId: Int,
    val name: String,
    val type: String,
    val address: String
): Parcelable