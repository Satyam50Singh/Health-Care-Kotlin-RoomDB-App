package satya.app.healthcareapproomdb.db.dao

import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.BookLabTestEntity

@Dao
interface LabBookingDao {

    @Query("SELECT * FROM lab_booking")
    fun getAllLabBookingRecord(): List<BookLabTestEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookLabTest(bokLabTestEntity: BookLabTestEntity)

    @Delete
    suspend fun cancelLabBooking(bookLabTestEntity: BookLabTestEntity)

    @Query("DELETE FROM lab_booking")
    suspend fun cancelAllLabBooking()
}