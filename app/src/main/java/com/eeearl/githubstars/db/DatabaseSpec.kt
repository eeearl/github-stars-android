package com.eeearl.githubstars.db

/**
 * DB 테이블 상수 정의
 */
class DatabaseSpec {
    interface GithubStar {
        companion object {
            const val TABLE_NAME = "GITHUB_STAR"
            const val USER_ID = "USER_ID"
            const val USER_NAME = "USER_NAME"
            const val USER_THUMBNAIL_URL = "USER_THUMBNAIL_URL"
        }
    }

    data class GithubStartData (val id: Int, val name: String, val thumbnailUrl: String)
}
