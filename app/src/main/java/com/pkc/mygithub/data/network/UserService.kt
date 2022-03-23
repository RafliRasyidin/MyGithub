package com.pkc.mygithub.data.network

import com.pkc.mygithub.data.model.SearchResponse
import com.pkc.mygithub.data.model.User
import com.pkc.mygithub.data.network.NetworkConfig.GITHUB_TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}