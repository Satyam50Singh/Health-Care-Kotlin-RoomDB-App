package satya.app.healthcareapproomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import satya.app.healthcareapproomdb.db.dao.*
import satya.app.healthcareapproomdb.db.entities.*

@Database(
    entities = [BookAnAppointmentEntity::class, BookAnAmbulanceEntity::class, BookLabTestEntity::class, OrderMedicineEntity::class,
        HealthArticleEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun doctorAppointmentDao(): DoctorAppointmentDao
    abstract fun ambulanceBookingDao(): AmbulanceBookingDao
    abstract fun labBookingDao(): LabBookingDao
    abstract fun orderMedicineDao(): OrderMedicineDao
    abstract fun healthArticleDao(): HealthArticleDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "health_care_database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }

}