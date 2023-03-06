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

    private val createArticleTableQuery =
        "create table health_article(uploadedBy text, uploadedOn text, articleHeading text, article text)"

    private val dropQueryPrefix = "DROP TABLE IF EXISTS"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createArticleTableQuery)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL("$dropQueryPrefix health_article")
        // Create tables again
        onCreate(sqLiteDatabase)
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
}