package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookLabTestEntity
import satya.app.healthcareapproomdb.repositories.BookLabTestRepository

class BookLabTestViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookLabTestRepository

    init {
        val labBookingDao = AppDatabase.getDatabase(application.applicationContext).labBookingDao()
        repository = BookLabTestRepository(labBookingDao)
    }

    fun getAllLabBooking(userId: Int): LiveData<List<BookLabTestEntity>> =
        repository.getAllLabTestBooking(userId)

    fun bookAnAppointment(bookLabTestEntity: BookLabTestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.bookLabTest(bookLabTestEntity)
            Log.e("TAG", "bookAnAppointment - insert call: $result ")
        }
    }

}