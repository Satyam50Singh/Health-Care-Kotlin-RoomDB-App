package satya.app.healthcareapproomdb.models

data class LabListModel(
    val labId: Int,
    val labTitle: String,
    val rating: Double,
    val noOfUsers: Int,
    val type: String,
    val address: String,
    val openingTime: String,
    val closingTime: String,
    var price: Int = 0,
    var homeVisitCharges: Int = 0
)
