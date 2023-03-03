package satya.app.healthcareapproomdb.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity
import satya.app.healthcareapproomdb.repositories.DoctorAppointmentRepository

class DoctorAppointmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DoctorAppointmentRepository

    init {
        val doctorAppointmentDao =
            AppDatabase.getDatabase(application.applicationContext).doctorAppointmentDao()
        repository = DoctorAppointmentRepository(doctorAppointmentDao)
    }

    fun getAllAppointments(userId: Int): LiveData<List<BookAnAppointmentEntity>> =
        repository.getAllAppointments(userId)

    fun bookAnAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertAppointment(bookAnAppointmentEntity)
            Log.e("TAG", "bookAnAppointment - insert call: $result ")
        }
    }

}