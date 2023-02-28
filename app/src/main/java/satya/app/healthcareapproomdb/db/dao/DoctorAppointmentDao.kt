package satya.app.healthcareapproomdb.db.dao

import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity

@Dao
interface DoctorAppointmentDao {

    @Query("SELECT * FROM doctor_appointment")
    fun getAllAppointmentsRecord(): List<BookAnAppointmentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity)

    @Delete
    suspend fun cancelAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity)

    @Query("DELETE FROM doctor_appointment")
    suspend fun cancelAllAppointments()
}