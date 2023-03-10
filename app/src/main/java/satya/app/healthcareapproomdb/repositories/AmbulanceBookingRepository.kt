package satya.app.healthcareapproomdb.repositories

import android.os.Looper
import androidx.lifecycle.*
import satya.app.healthcareapproomdb.db.DataHandler
import satya.app.healthcareapproomdb.db.dao.AmbulanceBookingDao
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity
import java.util.logging.Handler

class AmbulanceBookingRepository(
    private val ambulanceBookingDao: AmbulanceBookingDao
) {

    private val ambulanceBooking = MutableLiveData<DataHandler<List<BookAnAmbulanceEntity>>>()

    val bookings: LiveData<DataHandler<List<BookAnAmbulanceEntity>>>
        get() = ambulanceBooking

    suspend fun getAllAmbulanceBooking(userId: Int) {
        ambulanceBooking.postValue(DataHandler.LOADING())
        try {
            ambulanceBooking.postValue(
                DataHandler.SUCCESS(
                    ambulanceBookingDao.getAllAmbulanceBookingRecord(
                        userId
                    )
                )
            )
        } catch (e: Exception) {
            ambulanceBooking.postValue(DataHandler.ERROR("Something Wrong Happened!"))
            e.printStackTrace()
        }
    }

    suspend fun bookAnAmbulance(bookAnAmbulanceEntity: BookAnAmbulanceEntity): Long =
        ambulanceBookingDao.bookAmbulance(bookAnAmbulanceEntity)


    suspend fun cancelAmbulanceBooking(bookAnAmbulanceEntity: BookAnAmbulanceEntity) {
        ambulanceBookingDao.cancelAmbulanceBooking(bookAnAmbulanceEntity)
    }

    suspend fun cancelAllAmbulanceBooking() {
        ambulanceBookingDao.cancelAllAmbulanceBooking()
    }
}