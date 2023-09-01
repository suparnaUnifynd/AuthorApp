package com.example.authorapp.data.api

import com.example.authorapp.Constants
import com.example.authorapp.data.model.Author
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list")
    suspend fun getAuthor(
        @Query("page") page: Int=1,
        @Query("limit") limit: Int=Constants.PAGE_LIMIT
    ):Response<List<Author>>

}

