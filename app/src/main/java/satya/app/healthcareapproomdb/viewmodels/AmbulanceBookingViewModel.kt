package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity
import satya.app.healthcareapproomdb.repositories.AmbulanceBookingRepository

class AmbulanceBookingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AmbulanceBookingRepository

    init {
        val ambulanceBookingDao =
            AppDatabase.getDatabase(application.applicationContext).ambulanceBookingDao()
        repository = AmbulanceBookingRepository(ambulanceBookingDao)
    }

    fun getAllAppointments(userId: Int): LiveData<List<BookAnAmbulanceEntity>> =
        repository.getAllAmbulanceBooking(userId)

    fun bookAnAppointment(bookAnAmbulanceEntity: BookAnAmbulanceEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.bookAnAmbulance(bookAnAmbulanceEntity)
            Log.e("TAG", "bookAnAppointment - insert call: $result ")
        }
    }
}