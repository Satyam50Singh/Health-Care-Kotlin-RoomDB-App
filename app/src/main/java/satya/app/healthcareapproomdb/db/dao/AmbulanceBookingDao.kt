package satya.app.healthcareapproomdb.db.dao

import androidx.room.*
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity

@Dao
interface AmbulanceBookingDao {

    @Query("SELECT * FROM ambulance_booking")
    fun getAllAmbulanceBookingRecord(): List<BookAnAmbulanceEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun bookAmbulance(bookAnAmbulanceEntity: BookAnAmbulanceEntity)

    @Delete
    suspend fun cancelAmbulanceBooking(bookAnAmbulanceEntity: BookAnAmbulanceEntity)

    @Query("DELETE FROM ambulance_booking")
    suspend fun cancelAllAmbulanceBooking()
}