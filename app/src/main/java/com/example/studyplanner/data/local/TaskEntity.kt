package com.example.studyplanner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studyplanner.model.Priority
import com.example.studyplanner.model.StudyTask
import com.example.studyplanner.model.Subject

// One row in the "tasks" table
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val subject: String,   // saved as text, e.g. "MATH"
    val priority: String,  // saved as text, e.g. "HIGH"
    val dueDate: String,
    val isCompleted: Boolean = false
)

// Database row -> app model
fun TaskEntity.toStudyTask(): StudyTask = StudyTask(
    id = id,
    title = title,
    description = description,
    subject = Subject.valueOf(subject),
    priority = Priority.valueOf(priority),
    dueDate = dueDate,
    isCompleted = isCompleted
)

// App model -> database row
fun StudyTask.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    description = description,
    subject = subject.name,
    priority = priority.name,
    dueDate = dueDate,
    isCompleted = isCompleted
)