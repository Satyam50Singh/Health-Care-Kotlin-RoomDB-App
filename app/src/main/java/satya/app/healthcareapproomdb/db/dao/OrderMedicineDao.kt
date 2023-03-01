package satya.app.healthcareapproomdb.db.dao

import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.OrderMedicineEntity

@Dao
interface OrderMedicineDao {

    @Query("SELECT * FROM medicine_orders WHERE user_id LIKE :userId")
    fun getAllMedicineOrders(userId: Int): List<OrderMedicineEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookOrder(orderMedicineEntity: OrderMedicineEntity)

    @Delete
    suspend fun cancelOrder(orderMedicineEntity: OrderMedicineEntity)

    @Query("DELETE FROM medicine_orders")
    suspend fun cancelAllOrders()
}