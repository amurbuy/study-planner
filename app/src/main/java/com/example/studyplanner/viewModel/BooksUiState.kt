package com.example.studyplanner.viewmodel

import com.example.studyplanner.model.Book

sealed interface BooksUiState {
    object Loading : BooksUiState
    data class Success(val books: List<Book>) : BooksUiState
    data class Error(val message: String) : BooksUiState
    object Empty : BooksUiState
}