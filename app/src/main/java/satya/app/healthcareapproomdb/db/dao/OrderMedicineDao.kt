package satya.app.healthcareapproomdb.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.OrderMedicineEntity

@Dao
interface OrderMedicineDao {

    @Query("SELECT * FROM medicine_orders WHERE user_id LIKE :userId")
    fun getAllMedicineOrders(userId: Int): LiveData<List<OrderMedicineEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookOrder(orderMedicineEntity: OrderMedicineEntity): Long

    @Delete
    suspend fun cancelOrder(orderMedicineEntity: OrderMedicineEntity)

    @Query("DELETE FROM medicine_orders")
    suspend fun cancelAllOrders()
}