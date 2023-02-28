package satya.app.healthcareapproomdb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lab_booking")
data class BookLabTestEntity(
    @PrimaryKey(autoGenerate = true) val bookingId: Int?,
    @ColumnInfo(name = "user_id") val authUserId: Int,
    @ColumnInfo(name = "test_title") val testTitle: String,
    @ColumnInfo(name = "booked_by") val bookedBy: String,
    @ColumnInfo(name = "user_phone") val phone: String,
    @ColumnInfo(name = "lab_name") val labTitle: String,
    @ColumnInfo(name = "lab_address") val labAddress: String,
    @ColumnInfo(name = "test_cost") val price: Int,
    @ColumnInfo(name = "visit_type") val visitType: String,
    @ColumnInfo(name = "user_home_address") var homeAddress: String = ""
)