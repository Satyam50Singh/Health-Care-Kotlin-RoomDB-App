package satya.app.healthcareapproomdb.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LabTestModel(
    val testId: Int,
    val testTitle: String,
    val description: String,
    val sampleType: String,
    val fastingRequired: Boolean,
    val tubeType: String,
    var isExpandable: Boolean = false
): Parcelable