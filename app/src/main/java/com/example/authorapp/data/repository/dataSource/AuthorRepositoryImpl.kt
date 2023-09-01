package com.example.authorapp.data.repository.dataSource

import NetworkResponse
import com.example.authorapp.data.model.Author
import com.example.authorapp.domain.repository.AuthorRepository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AuthorRepositoryImpl(
    private val authorLocalDataSource: AuthorLocalDataSource,
    private val authorRemoteDataSource: AuthorRemoteDataSource
): AuthorRepository {

    override suspend fun getAuthors(pageNo:Int): NetworkResponse<List<Author>> {
        return sendResponse(authorRemoteDataSource.getAuthors(pageNo))
    }

    override suspend fun saveAuthors(author: List<Author>) {
        authorLocalDataSource.saveAuthorsToDB(author)
    }

    override fun getSavedAuthors(): Flow<List<Author>> {
        return authorLocalDataSource.getSavedAuthors()

    }

    override suspend fun deleteAll() {
        authorLocalDataSource.deleteALl()
    }
    

    private fun sendResponse(response:Response<List<Author>>):NetworkResponse<List<Author>>{
        if(response.isSuccessful){
            response.body()?.let { result->
                return NetworkResponse.Success(result)
            }
        }
        return NetworkResponse.Error(response.message())
    }
}