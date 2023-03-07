package satya.app.healthcareapproomdb.repositories

import androidx.lifecycle.LiveData
import satya.app.healthcareapproomdb.db.dao.OrderMedicineDao
import satya.app.healthcareapproomdb.db.entities.OrderMedicineEntity

class OrderMedicineRepository(
    private val orderMedicineDao: OrderMedicineDao
) {

    fun getAllOrders(userId: Int): LiveData<List<OrderMedicineEntity>> =
        orderMedicineDao.getAllMedicineOrders(userId)

    suspend fun bookOrderMedicine(orderMedicineEntity: OrderMedicineEntity): Long =
        orderMedicineDao.bookOrder(orderMedicineEntity)


    suspend fun deleteAppointment(orderMedicineEntity: OrderMedicineEntity) {
        orderMedicineDao.cancelOrder(orderMedicineEntity)
    }

    suspend fun deleteAllAppointment() = orderMedicineDao.cancelAllOrders()

}