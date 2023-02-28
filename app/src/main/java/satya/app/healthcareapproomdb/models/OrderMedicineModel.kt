package satya.app.healthcareapproomdb.models

data class OrderMedicineModel(
    val orderId: Int,
    val authUserId: Int,
    val bookedBy: String,
    val mobile: String?,
    val deliveryAddress: String?,
    val userEmail: String?,
    val paymentMode: String,
    val totalAmount: Int,
    val itemCount: Int,
    val medicineList: String,
    var isExpandable: Boolean = false
)