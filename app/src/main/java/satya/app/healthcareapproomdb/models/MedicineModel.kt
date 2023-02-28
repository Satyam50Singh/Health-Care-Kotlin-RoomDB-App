package satya.app.healthcareapproomdb.models

data class MedicineModel(
    val medicineId: Int,
    val medicineName: String,
    val weight: String,
    val medicineType: String,
    val piecePerPack: Int,
    val price: Int,
    var quantity: Int = 0,
) : java.io.Serializable