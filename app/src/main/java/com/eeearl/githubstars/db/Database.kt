package com.eeearl.githubstars.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * SQLite 생성 및 버전관리 그리고 기본적인 동작 처리
 */
class Database(context: Context) {

    companion object {
        private val TAG = Database::class.java.simpleName

        const val DATABASE_NAME = "github_stars.db"
        private const val DATABASE_VERSION = 1
    }

    private val mDBHelper: DatabaseHelper
    private var mDB: SQLiteDatabase? = null

    init {
        this.mDBHelper = DatabaseHelper(context)
        open()
    }

    @Throws(SQLException::class)
    fun open() {
        mDB = mDBHelper.writableDatabase
    }

    fun rawQuery(sql: String): Cursor {
        return mDB!!.rawQuery(sql, null)
    }

    fun insert(tableName: String, contentValues: ContentValues): Long {
        return mDB!!.insert(tableName, "", contentValues)
    }

    fun update(tableName: String, contentValues: ContentValues, where: String) {
        mDB!!.update(tableName, contentValues, where, null)
    }

    fun delete(tableName: String, where: String, args: Array<String>) {
        mDB!!.delete(tableName, where, args)
    }

    fun deleteAllRows(tableName: String) {
        mDB!!.delete(tableName, null, null)
    }

    private class DatabaseHelper internal constructor(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {

            var createLocationTableQry = "CREATE TABlE " + DatabaseSpec.GithubStar.TABLE_NAME + " ( "
            createLocationTableQry += "  " + DatabaseSpec.GithubStar.USER_ID               + " INTEGER     PRIMARY KEY"
            createLocationTableQry += ", " + DatabaseSpec.GithubStar.USER_NAME             + " TEXT        NOT NULL"
            createLocationTableQry += ", " + DatabaseSpec.GithubStar.USER_THUMBNAIL_URL    + " TEXT        NOT NULL"
            createLocationTableQry += " ); "

            db.execSQL(createLocationTableQry)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
            )
        }
    }
}
