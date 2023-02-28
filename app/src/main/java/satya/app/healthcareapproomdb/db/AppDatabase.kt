package satya.app.healthcareapproomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import satya.app.healthcareapproomdb.db.dao.DoctorAppointmentDao
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity

@Database(entities = [BookAnAppointmentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun doctorAppointmentDao(): DoctorAppointmentDao

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
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }

}