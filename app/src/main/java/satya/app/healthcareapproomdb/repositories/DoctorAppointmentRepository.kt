package satya.app.healthcareapproomdb.repositories;

import androidx.lifecycle.LiveData
import satya.app.healthcareapproomdb.db.dao.DoctorAppointmentDao
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity

class DoctorAppointmentRepository(
    private val doctorAppointmentDao: DoctorAppointmentDao
) {

    fun getAllAppointments(userId: Int): LiveData<List<BookAnAppointmentEntity>> =
        doctorAppointmentDao.getAllAppointmentsRecord(userId)

    suspend fun insertAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity) {
        doctorAppointmentDao.bookAppointment(bookAnAppointmentEntity)
    }

    suspend fun deleteAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity) {
        doctorAppointmentDao.cancelAppointment(bookAnAppointmentEntity)
    }

    suspend fun deleteAllAppointment() {
        doctorAppointmentDao.cancelAllAppointments()
    }
}
