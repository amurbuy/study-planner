package com.example.studyplanner.network

import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibraryApi {

    @GET("subjects/{subject}.json?limit=20")
    suspend fun getBooksBySubject(
        @Path("subject") subject: String
    ): SubjectResponseDto
}