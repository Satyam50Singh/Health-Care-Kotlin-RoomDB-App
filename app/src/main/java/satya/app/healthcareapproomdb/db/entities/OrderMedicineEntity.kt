package satya.app.healthcareapproomdb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine_orders")
data class OrderMedicineEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int?,
    @ColumnInfo(name = "user_id") val authUserId: Int,
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