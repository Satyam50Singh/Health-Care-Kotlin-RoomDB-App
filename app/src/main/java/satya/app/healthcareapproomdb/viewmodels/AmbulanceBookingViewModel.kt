package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.DataHandler
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity
import satya.app.healthcareapproomdb.repositories.AmbulanceBookingRepository
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager

class AmbulanceBookingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AmbulanceBookingRepository

    init {
        val ambulanceBookingDao =
            AppDatabase.getDatabase(application.applicationContext).ambulanceBookingDao()
        repository = AmbulanceBookingRepository(ambulanceBookingDao)

        val userId = PreferenceManager.getSharedPreferencesIntValues(
            application.applicationContext,
            Constants.PREF_USER_ID
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllAmbulanceBooking(userId)
        }
    }

    val ambulanceBooking: LiveData<DataHandler<List<BookAnAmbulanceEntity>>>
        get() = repository.bookings

    fun bookAnAppointment(bookAnAmbulanceEntity: BookAnAmbulanceEntity): Boolean {
        var result: Long = 0
        viewModelScope.launch(Dispatchers.IO) {
            result = repository.bookAnAmbulance(bookAnAmbulanceEntity)
            Log.e("TAG", "bookAnAppointment - insert call: $result ")
        }
        return result > 0L
    }
}