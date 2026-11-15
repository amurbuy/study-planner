package com.example.studyplanner.model


enum class Subject(val displayName: String) {
    MATH("Math"),
    PHYSICS("Physics"),
    PROGRAMMING("Programming"),
    ENGLISH("English"),
    HISTORY("History")
}

enum class Priority(val displayName: String) {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High")
}

data class StudyTask(
    val id: Int,
    val title: String,
    val description: String,
    val subject: Subject,
    val priority: Priority,
    val dueDate: String,
    val isCompleted: Boolean = false
)