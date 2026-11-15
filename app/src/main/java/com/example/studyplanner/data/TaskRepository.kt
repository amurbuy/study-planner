package com.example.studyplanner.data

import com.example.studyplanner.model.Priority
import com.example.studyplanner.model.StudyTask
import com.example.studyplanner.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object TaskRepository {

    private val _tasks = MutableStateFlow(sampleTasks())
    val tasks: StateFlow<List<StudyTask>> = _tasks.asStateFlow()

    private var nextId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1

    fun getTaskById(id: Int): StudyTask? =
        _tasks.value.find { it.id == id }

    fun addTask(
        title: String,
        description: String,
        subject: Subject,
        priority: Priority,
        dueDate: String
    ) {
        val newTask = StudyTask(
            id = nextId++,
            title = title,
            description = description,
            subject = subject,
            priority = priority,
            dueDate = dueDate
        )
        _tasks.update { it + newTask }
    }

    fun updateTask(task: StudyTask) {
        _tasks.update { list -> list.map { if (it.id == task.id) task else it } }
    }

    fun deleteTask(id: Int) {
        _tasks.update { list -> list.filterNot { it.id == id } }
    }

    fun toggleCompleted(id: Int) {
        _tasks.update { list ->
            list.map { if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    private fun sampleTasks(): List<StudyTask> = listOf(
        StudyTask(1, "Calculus homework", "Exercises 4.1–4.5 from the textbook", Subject.MATH, Priority.HIGH, "20 Nov 2026"),
        StudyTask(2, "Physics lab report", "Write report on pendulum experiment", Subject.PHYSICS, Priority.MEDIUM, "22 Nov 2026"),
        StudyTask(3, "Android project", "Finish MVVM and navigation", Subject.PROGRAMMING, Priority.HIGH, "25 Nov 2026"),
        StudyTask(4, "English essay", "500 words about technology in education", Subject.ENGLISH, Priority.LOW, "27 Nov 2026"),
        StudyTask(5, "History test prep", "Review chapters 5 and 6", Subject.HISTORY, Priority.MEDIUM, "18 Nov 2026", isCompleted = true),
        StudyTask(6, "Linear algebra quiz", "Matrices and determinants", Subject.MATH, Priority.MEDIUM, "19 Nov 2026", isCompleted = true)
    )
}