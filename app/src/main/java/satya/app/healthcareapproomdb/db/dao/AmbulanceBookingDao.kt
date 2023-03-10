package satya.app.healthcareapproomdb.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity

@Dao
interface AmbulanceBookingDao {

    @Query("SELECT * FROM ambulance_booking WHERE user_id LIKE :userId")
    suspend fun getAllAmbulanceBookingRecord(userId: Int): List<BookAnAmbulanceEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookAmbulance(bookAnAmbulanceEntity: BookAnAmbulanceEntity): Long

    @Delete
    suspend fun cancelAmbulanceBooking(bookAnAmbulanceEntity: BookAnAmbulanceEntity)

    @Query("DELETE FROM ambulance_booking")
    suspend fun cancelAllAmbulanceBooking()
}