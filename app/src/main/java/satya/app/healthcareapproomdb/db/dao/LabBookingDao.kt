package satya.app.healthcareapproomdb.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.BookLabTestEntity

@Dao
interface LabBookingDao {

    @Query("SELECT * FROM lab_booking WHERE user_id LIKE :userId")
    fun getAllLabBookingRecord(userId: Int): LiveData<List<BookLabTestEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookLabTest(bokLabTestEntity: BookLabTestEntity): Long

    @Delete
    suspend fun cancelLabBooking(bookLabTestEntity: BookLabTestEntity)

    @Query("DELETE FROM lab_booking")
    suspend fun cancelAllLabBooking()
}