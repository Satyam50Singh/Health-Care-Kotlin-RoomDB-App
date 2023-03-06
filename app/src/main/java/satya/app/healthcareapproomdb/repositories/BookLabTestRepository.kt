package satya.app.healthcareapproomdb.repositories

import androidx.lifecycle.LiveData
import satya.app.healthcareapproomdb.db.dao.LabBookingDao
import satya.app.healthcareapproomdb.db.entities.BookLabTestEntity

class BookLabTestRepository(
    private val labBookingDao: LabBookingDao
) {

    fun getAllLabTestBooking(userId: Int): LiveData<List<BookLabTestEntity>> =
        labBookingDao.getAllLabBookingRecord(userId)

    suspend fun bookLabTest(bookLabTestEntity: BookLabTestEntity): Long =
        labBookingDao.bookLabTest(bookLabTestEntity)


    suspend fun cancelLabBooking(bookLabTestEntity: BookLabTestEntity) {
        labBookingDao.cancelLabBooking(bookLabTestEntity)
    }

    suspend fun cancelAllLabBooking() = labBookingDao.cancelAllLabBooking()
}