package com.example.authorapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorapp.domain.use_cases.AuthorUseCasesWrapper

class AuthorViewModelFectory(
    private val app: Application,
    private val useCasesWrapper: AuthorUseCasesWrapper
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorViewModel(app, useCasesWrapper ) as T
    }
}