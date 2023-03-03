package satya.app.healthcareapproomdb.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity

@Dao
interface DoctorAppointmentDao {

    @Query("SELECT * FROM doctor_appointment WHERE user_id LIKE :userId")
    fun getAllAppointmentsRecord(userId: Int): LiveData<List<BookAnAppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity): Long

    @Delete
    suspend fun cancelAppointment(bookAnAppointmentEntity: BookAnAppointmentEntity)

    @Query("DELETE FROM doctor_appointment")
    suspend fun cancelAllAppointments()
}