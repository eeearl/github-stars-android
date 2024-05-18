package com.eeearl.githubstars.net

import com.eeearl.githubstars.net.model.SearchUserRequest
import com.eeearl.githubstars.net.model.SearchUserRequestData
import com.eeearl.githubstars.net.model.SearchUserResponse
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * API 호출 클래스
 */
object ApiClient {

    private const val baseUrl = "https://api.github.com/"

    @JvmStatic
    private var httpClient: OkHttpClient = OkHttpClient().newBuilder().build()
    @JvmStatic
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create())
        .client(httpClient)

        .build()

    fun searchUserInGithub(request: SearchUserRequestData, callback: Callback<SearchUserResponse>) {
        retrofit.create(SearchUserRequest::class.java)
            .searchRepositories(request.username, request.order, request.page, request.perPage)
            .enqueue(callback)
    }
}
