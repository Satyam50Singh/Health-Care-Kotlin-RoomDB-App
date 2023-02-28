package satya.app.healthcareapproomdb.models

data class BookAnAppointmentModel(
    val appointmentId: Int, // primary key
    val authUserId: Int,
    val patientName: String?,
    val date: String,
    val day: String,
    val timeSlot: String,
    val doctorId: Int,
    val doctorName: String,
    val doctorCategory: String,
)