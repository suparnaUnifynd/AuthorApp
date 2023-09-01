package com.example.authorapp.domain.use_cases

import com.example.authorapp.domain.repository.AuthorRepository

class DeleteAllAuthorsCase(private val authorRepository: AuthorRepository) {

    suspend fun execute()= authorRepository.deleteAll()
}