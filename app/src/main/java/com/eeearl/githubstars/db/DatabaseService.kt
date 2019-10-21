package com.eeearl.githubstars.db

import android.content.ContentValues
import android.content.Context
import com.eeearl.githubstars.db.DatabaseSpec.GithubStar
import com.eeearl.githubstars.ui.local.SearchUserLocalCallback
import com.eeearl.githubstars.ui.SearchUserRowLocalItem

/**
 * 앱에 필요한 DB 질의 정의
 */
class DatabaseService private constructor(private val context: Context) {

    private val mDB: Database = Database(context)

    companion object {
        private var mInstance: DatabaseService? = null

        fun getInstance(context: Context): DatabaseService {
            if (mInstance == null) {
                mInstance = DatabaseService(context)
            }
            return mInstance!!
        }
    }

    /**
     * 저장된 유저 질의
     */
    fun selectGithubUsers(searchText: String, callback: SearchUserLocalCallback<SearchUserRowLocalItem>) {
        var selectLayerQry = ""
        selectLayerQry += "SELECT * FROM " + GithubStar.TABLE_NAME
        selectLayerQry += if (searchText.isNotEmpty()) " WHERE ${GithubStar.USER_NAME} LIKE '%$searchText%' " else ""
        selectLayerQry += " ORDER BY "
        selectLayerQry += " ${GithubStar.USER_ID} "
        selectLayerQry += " DESC "
        selectLayerQry += " ; "

        val cursor = mDB.rawQuery(selectLayerQry)
        val list = mutableListOf<SearchUserRowLocalItem>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(GithubStar.USER_ID))
            val name = cursor.getString(cursor.getColumnIndex(GithubStar.USER_NAME))
            val thumbnail = cursor.getString(cursor.getColumnIndex(GithubStar.USER_THUMBNAIL_URL))
            val item = SearchUserRowLocalItem(id, name, thumbnail, true)

            list.add(item)
        }

        callback.onFetch(list)
    }

    /**
     * 즐겨찾기 사용자로 저장
     */
    fun insertUser(id: Int, name: String, thumbnailUrl: String) {
        val cv = ContentValues()
        cv.put(GithubStar.USER_ID, id)
        cv.put(GithubStar.USER_NAME, name)
        cv.put(GithubStar.USER_THUMBNAIL_URL, thumbnailUrl)

        mDB.insert(GithubStar.TABLE_NAME, cv)
    }

    /**
     * 저장된 사용자 여부 확인
     */
    fun existUser(id: Int): Boolean {
        var selectLayerQry = ""
        selectLayerQry += "SELECT * FROM " + GithubStar.TABLE_NAME
        selectLayerQry += " WHERE " + GithubStar.USER_ID + " = " + id
        selectLayerQry += " ORDER BY "
        selectLayerQry += GithubStar.USER_ID
        selectLayerQry += " DESC "
        selectLayerQry += " ; "

        val cursor = mDB.rawQuery(selectLayerQry)

        return cursor.count > 0
    }

    /**
     * 즐겨찾기 사용자 삭제
     */
    fun deleteUser(id: Int) {
        mDB.delete(GithubStar.TABLE_NAME, "${GithubStar.USER_ID}=?", arrayOf("$id"))
    }
}
