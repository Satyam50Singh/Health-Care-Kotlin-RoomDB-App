package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.OrderMedicineEntity
import satya.app.healthcareapproomdb.repositories.OrderMedicineRepository

class OrderMedicineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrderMedicineRepository

    init {
        val orderMedicineDao =
            AppDatabase.getDatabase(application.applicationContext).orderMedicineDao()
        repository = OrderMedicineRepository(orderMedicineDao)
    }

    fun getAllOrders(userId: Int): LiveData<List<OrderMedicineEntity>> =
        repository.getAllOrders(userId)

    fun bookAnMedicineOrder(orderMedicineEntity: OrderMedicineEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.bookOrderMedicine(orderMedicineEntity)
            Log.e("TAG", "orderMedicineEntity - insert call: $result ")
        }
    }

}