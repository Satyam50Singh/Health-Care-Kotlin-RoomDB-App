package satya.app.healthcareapproomdb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ambulance_booking")
data class BookAnAmbulanceEntity(
    @PrimaryKey(autoGenerate = true) val bookingId: Int,
    @ColumnInfo(name = "user_id") val authUserId: Int,
    @ColumnInfo(name = "booked_by") val bookedBy: String,
    @ColumnInfo(name = "ambulance_id") val ambulanceId: Int,
    @ColumnInfo(name = "ambulance_name") val ambulanceName: String,
    @ColumnInfo(name = "ambulance_type") val ambulanceType: String,
    @ColumnInfo(name = "ambulance_address") val ambulanceAddress: String,
    @ColumnInfo(name = "pick_up_location") val pickUpLocation: String,
    @ColumnInfo(name = "pick_up_date") val pickUpDate: String,
    @ColumnInfo(name = "pick_up_time") val pickUpTime: String,
    @ColumnInfo(name = "drop_location") val dropLocation: String,
    @ColumnInfo(name = "user_phone") val phone: String
)