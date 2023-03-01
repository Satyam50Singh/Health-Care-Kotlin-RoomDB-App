package satya.app.healthcareapproomdb.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
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

    private val dropQueryPrefix = "DROP TABLE IF EXISTS"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserTableQuery)
        db?.execSQL(createArticleTableQuery)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL("$dropQueryPrefix users")
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