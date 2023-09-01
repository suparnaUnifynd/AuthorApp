package com.example.authorapp.domain.use_cases

import com.example.authorapp.data.model.Author
import com.example.authorapp.domain.repository.AuthorRepository


class SaveAuthorsUseCase(private val authorRepository: AuthorRepository) {
    suspend fun execute(authors: List<Author>) = authorRepository.saveAuthors(authors)
}