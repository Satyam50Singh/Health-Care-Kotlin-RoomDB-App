package satya.app.healthcareapproomdb.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import satya.app.healthcareapproomdb.models.HealthArticleModel
import satya.app.healthcareapproomdb.models.OrderMedicineModel
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
    private val createArticleTableQuery =
        "create table health_article(uploadedBy text, uploadedOn text, articleHeading text, article text)"
    private val createOrderTableQuery =
        "create table medicine_orders(orderId INTEGER PRIMARY KEY, authUserId INTEGER, bookedBy TEXT, mobile TEXT, deliveryAddress TEXT, userEmail TEXT, paymentMode TEXT, totalAmount INTEGER, itemCount INTEGER, medicineList TEXT)"

    private val dropQueryPrefix = "DROP TABLE IF EXISTS"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserTableQuery)
        db?.execSQL(createArticleTableQuery)
        db?.execSQL(createOrderTableQuery)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL("$dropQueryPrefix users")
        sqLiteDatabase?.execSQL("$dropQueryPrefix health_article")
        sqLiteDatabase?.execSQL("$dropQueryPrefix medicine_orders")
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