package satya.app.healthcareapproomdb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctor_appointment")
data class BookAnAppointmentEntity(
    @PrimaryKey(autoGenerate = true) val appointmentId: Int?, // primary key
    @ColumnInfo(name = "user_id") val authUserId: Int,
    @ColumnInfo(name = "patient_name") val patientName: String?,
    @ColumnInfo(name = "appointment_date") val date: String,
    @ColumnInfo(name = "appointment_day") val day: String,
    @ColumnInfo(name = "appointment_time_slot") val timeSlot: String,
    @ColumnInfo(name = "doctor_id") val doctorId: Int,
    @ColumnInfo(name = "doctor_name") val doctorName: String,
    @ColumnInfo(name = "doctor_type") val doctorCategory: String,
)