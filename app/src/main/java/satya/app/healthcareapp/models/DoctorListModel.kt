package satya.app.healthcareapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoctorListModel(
    val doctorId: Int, // primary key
    val name: String,
    val doctorHistory: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val category: String,
    val rating: Int = 1,
) : Parcelable