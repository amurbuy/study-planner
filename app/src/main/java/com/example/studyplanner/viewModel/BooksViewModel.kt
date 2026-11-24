package com.example.studyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.data.BookRepository
import com.example.studyplanner.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BooksViewModel(
    private val repository: BookRepository = BookRepository()
) : ViewModel() {

    private val _selectedSubject = MutableStateFlow<Subject?>(null)
    val selectedSubject: StateFlow<Subject?> = _selectedSubject.asStateFlow()

    private val _uiState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val uiState: StateFlow<BooksUiState> = _uiState.asStateFlow()

    fun onSubjectSelected(subject: Subject) {
        if (subject == _selectedSubject.value) return
        _selectedSubject.value = subject
        loadBooks(subject)
    }

    fun retry() {
        _selectedSubject.value?.let { loadBooks(it) }
    }

    private fun loadBooks(subject: Subject) {
        viewModelScope.launch {
            _uiState.value = BooksUiState.Loading

            val newState = try {
                val books = repository.getBooksBySubject(subject)
                if (books.isEmpty()) BooksUiState.Empty else BooksUiState.Success(books)
            } catch (e: IOException) {
                BooksUiState.Error("No internet connection. Check your network and try again.")
            } catch (e: HttpException) {
                BooksUiState.Error("Server error (${e.code()}). Please try again later.")
            } catch (e: Exception) {
                BooksUiState.Error("Something went wrong. Please try again.")
            }

            // Ignore an old response if the user has already picked another subject
            if (subject == _selectedSubject.value) {
                _uiState.value = newState
            }
        }
    }
}