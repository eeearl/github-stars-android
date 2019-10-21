package com.eeearl.githubstars.net.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchUserRequest {

    @Headers("Accept: application/vnd.github.v3.text-match+json")
    @GET("/search/users")
    fun searchRepositories(
        @Query("q") username: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<SearchUserResponse>

}

data class SearchUserRequestData (
    private val _username: String,
    val order: String,
    val page: Int,
    val perPage: Int
) {
    var username: String = ""
        get() = "$_username in:fullname"
}
