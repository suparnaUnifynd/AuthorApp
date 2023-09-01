package com.example.authorapp.domain.use_cases

import NetworkResponse
import com.example.authorapp.data.model.Author
import com.example.authorapp.domain.repository.AuthorRepository

class GetAuthorsUseCase(private val authorRepository: AuthorRepository) {

    suspend fun execute(pageNo:Int): NetworkResponse<List<Author>> = authorRepository.getAuthors(pageNo)
}