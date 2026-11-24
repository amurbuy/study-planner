package com.example.studyplanner.data

import com.example.studyplanner.model.Book
import com.example.studyplanner.model.Subject
import com.example.studyplanner.network.OpenLibraryApi
import com.example.studyplanner.network.RetrofitInstance
import com.example.studyplanner.network.WorkDto

class BookRepository(
    private val api: OpenLibraryApi = RetrofitInstance.api
) {

    suspend fun getBooksBySubject(subject: Subject): List<Book> {
        val response = api.getBooksBySubject(subject.toApiName())
        return response.works.map { it.toBook() }
    }

    private fun Subject.toApiName(): String = when (this) {
        Subject.MATH -> "mathematics"
        Subject.PHYSICS -> "physics"
        Subject.PROGRAMMING -> "computer_science"
        Subject.ENGLISH -> "english_language"
        Subject.HISTORY -> "history"
    }

    private fun WorkDto.toBook(): Book = Book(
        title = title ?: "Untitled",
        author = authors.firstOrNull()?.name ?: "Unknown author",
        year = firstPublishYear,
        coverUrl = coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
    )
}