package com.example.authorapp.domain.use_cases

import com.example.authorapp.data.model.Author
import com.example.authorapp.data.model.AuthorDto
import com.example.authorapp.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow

class GetSavedAuthorsUseCase(private val authorRepository: AuthorRepository) {
    suspend fun execute(): Flow<List<Author>> = authorRepository.getSavedAuthors()
}