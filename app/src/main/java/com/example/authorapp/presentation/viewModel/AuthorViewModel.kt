package com.example.authorapp.presentation.viewModel

import NetworkResponse
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.authorapp.Constants
import com.example.authorapp.Utils
import com.example.authorapp.data.model.Author
import com.example.authorapp.data.model.AuthorDto
import com.example.authorapp.domain.use_cases.AuthorUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel
@Inject constructor(
    private val app: Application,
    private val useCasesWrapper: AuthorUseCasesWrapper

    ) : AndroidViewModel(app) {

    val authors: MutableLiveData<NetworkResponse<List<Author>>> = MutableLiveData()



    fun getAuthors(pageNo:Int) = viewModelScope.launch(Dispatchers.IO) {
        authors.postValue(NetworkResponse.Loading())
        try {
            if (Utils.isNetworkAvailable(app)) {
                val response = useCasesWrapper.getAuthorsUseCase.execute(pageNo)
                Log.d("MainActivity","authorList response success  ${response.message} ")
                Log.d("MainActivity","authorList response success  ${response.data} ")
                Log.d("MainActivity","authorList response success  ${response} ")

                authors.postValue(response)
                response.data?.let { saveAuthors(it) }
            } else {
                authors.postValue(NetworkResponse.Error(Constants.NO_INTERNET_MSG))
            }

        } catch (e: Exception) {
            Log.d("MainActivity","authorList response  ${e.message.toString()} ")
            authors.postValue(NetworkResponse.Error(e.message.toString()))
        }
    }




    fun saveAuthors(authors: List<Author>) = viewModelScope.launch {
        useCasesWrapper.saveAuthorsUseCase.execute(authors)
    }


    fun getSavedAuthors() = liveData {
        useCasesWrapper.getSavedAuthorsUseCase.execute().collect {
            emit(it)
        }
    }

}