package satya.app.healthcareapproomdb.models

data class BookAnAmbulanceModel(
    val bookingId: Int, // primary key
    val authUserId : Int,
    val bookedBy: String,
    val ambulanceId: Int,
    val ambulanceName: String,
    val ambulanceType: String,
    val ambulanceAddress: String,
    val pickUpLocation: String,
    val pickUpDate: String,
    val pickUpTime: String,
    val dropLocation: String,
    val phone: String
)