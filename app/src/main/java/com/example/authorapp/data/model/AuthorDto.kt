package com.example.authorapp.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


class AuthorDto : ArrayList<AuthorDto.AuthorDtoItem>(){


    data class AuthorDtoItem(
        @SerializedName("author")
        val author: String,
        @SerializedName("download_url")
        val downloadUrl: String,
        @SerializedName("height")
        val height: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("width")
        val width: Int
    )
}