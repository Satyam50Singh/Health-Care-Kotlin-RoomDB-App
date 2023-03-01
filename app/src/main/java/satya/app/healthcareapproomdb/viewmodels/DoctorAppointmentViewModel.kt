package satya.app.healthcareapproomdb.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity
import satya.app.healthcareapproomdb.repositories.DoctorAppointmentRepository

class DoctorAppointmentViewModel : ViewModel() {

    fun getAllAppointments(context: Context, userId: Int): LiveData<List<BookAnAppointmentEntity>> {
        return DoctorAppointmentRepository(
            doctorAppointmentDao = AppDatabase.getDatabase(context).doctorAppointmentDao()
        ).getAllAppointments(userId)
    }

}