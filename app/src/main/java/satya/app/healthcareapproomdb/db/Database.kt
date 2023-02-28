package satya.app.healthcareapproomdb.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import satya.app.healthcareapproomdb.models.*
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager

class Database(
    private val context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val TAG = "Database"

    private val createUserTableQuery =
        "create table users(userId INTEGER PRIMARY KEY, username text, email text, password text)"
    private val createAppointmentTableQuery =
        "create table appointments(appointmentId INTEGER PRIMARY KEY, authUserId INTEGER, patientName text, date text, day text, timeSlot text, doctorId INTEGER, doctorName text, doctorCategory text)"
    private val createAmbulanceTableQuery =
        "create table ambulance_booking(bookingId INTEGER PRIMARY KEY, authUserId INTEGER, bookedBy text, ambulanceId INTEGER, ambulanceName text, ambulanceType text,  ambulanceAddress text, pickUpLocation text, pickUpDate text, pickUpTime text, dropLocation text, phone text)"
    private val createBookLabTableQuery =
        "create table lab_booking(bookingId INTEGER PRIMARY KEY, authUserId INTEGER, testTitle text, bookedBy text, phone text, labTitle text, labAddress text, price INTEGER, visitType text, homeAddress text)"
    private val createArticleTableQuery =
        "create table health_article(uploadedBy text, uploadedOn text, articleHeading text, article text)"
    private val createOrderTableQuery =
        "create table medicine_orders(orderId INTEGER PRIMARY KEY, authUserId INTEGER, bookedBy TEXT, mobile TEXT, deliveryAddress TEXT, userEmail TEXT, paymentMode TEXT, totalAmount INTEGER, itemCount INTEGER, medicineList TEXT)"

    private val dropQueryPrefix = "DROP TABLE IF EXISTS"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserTableQuery)
        db?.execSQL(createAppointmentTableQuery)
        db?.execSQL(createAmbulanceTableQuery)
        db?.execSQL(createBookLabTableQuery)
        db?.execSQL(createArticleTableQuery)
        db?.execSQL(createOrderTableQuery)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL("$dropQueryPrefix users")
        sqLiteDatabase?.execSQL("$dropQueryPrefix appointments")
        sqLiteDatabase?.execSQL("$dropQueryPrefix ambulance_booking")
        sqLiteDatabase?.execSQL("$dropQueryPrefix lab_booking")
        sqLiteDatabase?.execSQL("$dropQueryPrefix health_article")
        Log.e(TAG, "onUpgrade")

        // Create tables again
        onCreate(sqLiteDatabase)
    }

    // method to do user registration
    fun registerUser(username: String, email: String, password: String): Boolean {
        val cv = ContentValues()
        cv.put("username", username)
        cv.put("email", email)
        cv.put("password", password)

        val db: SQLiteDatabase = writableDatabase
        val result = db.insert("users", null, cv)
        Log.e(TAG, "registerUser: $result")
        db.close()
        return result >= 1L
    }

    // check whether the user exists in the database.
    fun checkUser(email: String): Boolean {
        val db = readableDatabase
        val cursor: Cursor =
            db.query("users", arrayOf("email"), "email = ?", arrayOf(email), null, null, null)
        val cursorCount: Int = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }

    // method to do user login
    fun loginUser(username: String, password: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "select * from users where email=? and password=?", arrayOf(
                username, password
            )
        )
        Log.e(TAG, "loginUser: ${cursor.columnNames.forEach { println(it) }}")
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(0)
            val username = cursor.getString(1)
            Log.e(TAG, "loginUser: $username")
            if (context != null) {
                PreferenceManager.setSharedPreferences(
                    context, Constants.PREF_USER_ID, userId
                )
                PreferenceManager.setSharedPreferences(
                    context, Constants.PREF_USERNAME, username
                )
            }
            return 1
        }

        return 0
    }

    // method to book an doctor appointment
    fun bookAnAppointment(bookAnAppointmentModel: BookAnAppointmentModel): Boolean {
        val cv = ContentValues()
        cv.put("authUserId", bookAnAppointmentModel.authUserId)
        cv.put("patientName", bookAnAppointmentModel.patientName)
        cv.put("date", bookAnAppointmentModel.date)
        cv.put("day", bookAnAppointmentModel.day)
        cv.put("timeSlot", bookAnAppointmentModel.timeSlot)
        cv.put("doctorId", bookAnAppointmentModel.doctorId)
        cv.put("doctorName", bookAnAppointmentModel.doctorName)
        cv.put("doctorCategory", bookAnAppointmentModel.doctorCategory)

        val db: SQLiteDatabase = writableDatabase
        val result = db.insert("appointments", null, cv)
        Log.e(TAG, "bookAnAppointment: $result")
        db.close()
        return result >= 1L
    }

    // method to book an ambulance
    fun bookAnAmbulance(bookAnAmbulanceModel: BookAnAmbulanceModel): Boolean {
        val cv = ContentValues()
        cv.put("authUserId", bookAnAmbulanceModel.authUserId)
        cv.put("bookedBy", bookAnAmbulanceModel.bookedBy)
        cv.put("ambulanceId", bookAnAmbulanceModel.ambulanceId)
        cv.put("ambulanceName", bookAnAmbulanceModel.ambulanceName)
        cv.put("ambulanceType", bookAnAmbulanceModel.ambulanceType)
        cv.put("ambulanceAddress", bookAnAmbulanceModel.ambulanceAddress)
        cv.put("pickUpLocation", bookAnAmbulanceModel.pickUpLocation)
        cv.put("pickUpDate", bookAnAmbulanceModel.pickUpDate)
        cv.put("pickUpTime", bookAnAmbulanceModel.pickUpTime)
        cv.put("dropLocation", bookAnAmbulanceModel.dropLocation)
        cv.put("phone", bookAnAmbulanceModel.phone)

        val db: SQLiteDatabase = writableDatabase
        val result = db.insert("ambulance_booking", null, cv)
        Log.e(TAG, "bookAnAmbulance: $result")
        db.close()
        return result >= 1L
    }

    // method to book an lab test
    fun bookLabTest(bookLabTestModel: BookLabTestModel): Boolean {
        val cv = ContentValues()
        cv.put("authUserId", bookLabTestModel.authUserId)
        cv.put("testTitle", bookLabTestModel.testTitle)
        cv.put("bookedBy", bookLabTestModel.bookedBy)
        cv.put("phone", bookLabTestModel.phone)
        cv.put("labTitle", bookLabTestModel.labTitle)
        cv.put("labAddress", bookLabTestModel.labAddress)
        cv.put("price", bookLabTestModel.price)
        cv.put("visitType", bookLabTestModel.visitType)
        cv.put("homeAddress", bookLabTestModel.homeAddress)
        val db: SQLiteDatabase = writableDatabase
        val result = db.insert("lab_booking", null, cv)
        Log.e(TAG, "bookLabTest: $result")
        db.close()
        return result >= 1L
    }


    // method to order medicine from cart
    fun orderMedicine(orderMedicineModel: OrderMedicineModel): Boolean {
        val cv = ContentValues()
        cv.put("authUserId", orderMedicineModel.authUserId)
        cv.put("bookedBy", orderMedicineModel.bookedBy)
        cv.put("mobile", orderMedicineModel.mobile)
        cv.put("deliveryAddress", orderMedicineModel.deliveryAddress)
        cv.put("userEmail", orderMedicineModel.userEmail)
        cv.put("paymentMode", orderMedicineModel.paymentMode)
        cv.put("totalAmount", orderMedicineModel.totalAmount)
        cv.put("itemCount", orderMedicineModel.itemCount)
        cv.put("medicineList", orderMedicineModel.medicineList)
        val db: SQLiteDatabase = writableDatabase
        val result = db.insert("medicine_orders", null, cv)
        Log.e(TAG, "orderMedicine: $result")
        db.close()
        return result >= 1L
    }

    // method to add new articles
    fun addHealthArticle(context: Context, healthArticleModel: HealthArticleModel): Boolean {
        val cv = ContentValues()
        cv.put(
            "uploadedBy", PreferenceManager.getSharedPreferences(context, Constants.PREF_USERNAME)
        )
        cv.put("uploadedOn", healthArticleModel.uploadedOn)
        cv.put("articleHeading", healthArticleModel.title)
        cv.put("article", healthArticleModel.article)

        val db: SQLiteDatabase = writableDatabase
        val result = db.insert("health_article", null, cv)
        Log.e(TAG, "addHealthArticle: $result")
        db.close()
        return result >= 1L
    }

    // method to get the articles . .
    fun getHealthArticles(): Cursor? {
        try {
            val db = readableDatabase
            return db.rawQuery("SELECT * FROM health_article ORDER BY uploadedOn DESC", null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    // method to get the list of doctor appointment
    fun getDoctorsAppointmentList(): ArrayList<BookAnAppointmentModel> {
        val records = ArrayList<BookAnAppointmentModel>()
        try {
            val userId =
                PreferenceManager.getSharedPreferencesIntValues(context!!, Constants.PREF_USER_ID)
                    .toString()
            val db = readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM appointments WHERE authUserId=?", arrayOf(userId)
            )

            while (cursor?.moveToNext() == true) {
                val bookAnAppointmentModel = BookAnAppointmentModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8),
                )
                records.add(bookAnAppointmentModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return records
    }

    // method to get the list of ambulance booking
    fun getBookedAmbulanceList(): ArrayList<BookAnAmbulanceModel> {
        val records = ArrayList<BookAnAmbulanceModel>()

        try {
            val userId =
                PreferenceManager.getSharedPreferencesIntValues(context!!, Constants.PREF_USER_ID)
                    .toString()
            val db = readableDatabase
            val cursor =
                db.rawQuery("SELECT * FROM ambulance_booking WHERE authUserId=?", arrayOf(userId))

            while (cursor?.moveToNext() == true) {
                val bookAnAmbulanceModel = BookAnAmbulanceModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11)
                )
                records.add(bookAnAmbulanceModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return records
    }

    // method to get the list of labs test booking
    fun getBookedLabTestList(): ArrayList<BookLabTestModel> {
        val records = ArrayList<BookLabTestModel>()

        try {
            val userId =
                PreferenceManager.getSharedPreferencesIntValues(context!!, Constants.PREF_USER_ID)
                    .toString()
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM lab_booking WHERE authUserId=?", arrayOf(userId))

            while (cursor?.moveToNext() == true) {
                val bookLabTestModel = BookLabTestModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getString(8),
                    cursor.getString(9),
                )
                records.add(bookLabTestModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return records
    }

    // method to get the list of medicine orders
    fun getMedicineOrdersList(): ArrayList<OrderMedicineModel> {
        val records = ArrayList<OrderMedicineModel>()
        try {
            val userId =
                PreferenceManager.getSharedPreferencesIntValues(context!!, Constants.PREF_USER_ID)
                    .toString()
            val db = readableDatabase
            val cursor =
                db.rawQuery("SELECT * FROM medicine_orders WHERE authUserId=?", arrayOf(userId))
            while (cursor?.moveToNext() == true) {
                val orderMedicineModel = OrderMedicineModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getString(9)
                )
                records.add(orderMedicineModel)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return records
    }

    fun changePassword(enteredPassword: String, newPassword: String): Boolean {
        try {
            val cv = ContentValues()
            cv.put("password", newPassword)
            val db = writableDatabase
            val result = db.update("users", cv, "password = ?", arrayOf(enteredPassword))
            Log.e(TAG, "changePassword: $result")

            return result > 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}