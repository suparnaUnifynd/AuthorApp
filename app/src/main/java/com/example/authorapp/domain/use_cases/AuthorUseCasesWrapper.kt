package com.example.authorapp.domain.use_cases

data class AuthorUseCasesWrapper (
    
    val getAuthorsUseCase: GetAuthorsUseCase,
    val getSavedAuthorsUseCase: GetSavedAuthorsUseCase,
    val saveAuthorsUseCase: SaveAuthorsUseCase,
    val deleteAllUseCase: DeleteAllAuthorsCase
)