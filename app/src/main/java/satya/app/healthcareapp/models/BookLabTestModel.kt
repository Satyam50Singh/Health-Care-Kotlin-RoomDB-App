package satya.app.healthcareapp.models

data class BookLabTestModel(
    val bookingId: Int, // Primary ID
    val authUserId: Int,
    val testTitle: String,
    val bookedBy: String,
    val phone: String,
    val labTitle: String,
    val labAddress: String,
    val price: Int,
    val visitType: String,
    var homeAddress: String = ""
)