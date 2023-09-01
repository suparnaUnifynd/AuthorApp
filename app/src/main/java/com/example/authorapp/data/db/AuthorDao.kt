package com.example.authorapp.data.db

import androidx.room.*
import com.example.authorapp.data.model.Author
import com.example.authorapp.data.model.AuthorDto
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthors(authors: List<Author>)

    @Query("SELECT * FROM Author")
    fun getAllAuthors(): Flow<List<Author>>

    @Delete
    suspend fun deleteAuthor(author: Author)

    @Query("DELETE FROM Author")
    suspend fun deleteAll()

}