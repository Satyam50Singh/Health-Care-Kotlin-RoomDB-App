package satya.app.healthcareapproomdb.repositories

import androidx.lifecycle.LiveData
import satya.app.healthcareapproomdb.db.dao.AmbulanceBookingDao
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity

class AmbulanceBookingRepository(
    private val ambulanceBookingDao: AmbulanceBookingDao
) {

    fun getAllAmbulanceBooking(userId: Int): LiveData<List<BookAnAmbulanceEntity>> =
        ambulanceBookingDao.getAllAmbulanceBookingRecord(userId)

    suspend fun bookAnAmbulance(bookAnAmbulanceEntity: BookAnAmbulanceEntity): Long =
        ambulanceBookingDao.bookAmbulance(bookAnAmbulanceEntity)


    suspend fun cancelAmbulanceBooking(bookAnAmbulanceEntity: BookAnAmbulanceEntity) {
        ambulanceBookingDao.cancelAmbulanceBooking(bookAnAmbulanceEntity)
    }

    suspend fun cancelAllAmbulanceBooking() {
        ambulanceBookingDao.cancelAllAmbulanceBooking()
    }
}