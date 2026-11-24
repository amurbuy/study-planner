package com.example.studyplanner.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectResponseDto(
    val name: String? = null,
    @SerialName("work_count")
    val workCount: Int? = null,
    val works: List<WorkDto> = emptyList()
)

@Serializable
data class WorkDto(
    val key: String? = null,
    val title: String? = null,
    @SerialName("cover_id")
    val coverId: Long? = null,
    @SerialName("first_publish_year")
    val firstPublishYear: Int? = null,
    val authors: List<AuthorDto> = emptyList()
)

@Serializable
data class AuthorDto(
    val name: String? = null
)