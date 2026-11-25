package com.example.studyplanner.data

import android.content.Context
import com.example.studyplanner.data.local.StudyPlannerDatabase
import com.example.studyplanner.data.local.TaskDao
import com.example.studyplanner.data.local.TaskEntity
import com.example.studyplanner.data.local.toEntity
import com.example.studyplanner.data.local.toStudyTask
import com.example.studyplanner.model.Priority
import com.example.studyplanner.model.StudyTask
import com.example.studyplanner.model.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TaskRepository {

    private lateinit var taskDao: TaskDao

    // Called once from MainActivity before any screen is shown
    fun init(context: Context) {
        taskDao = StudyPlannerDatabase.getDatabase(context).taskDao()
    }

    val tasks: Flow<List<StudyTask>>
        get() = taskDao.getAllTasks().map { entities ->
            entities.map { it.toStudyTask() }
        }

    suspend fun getTaskById(id: Int): StudyTask? =
        taskDao.getTaskById(id)?.toStudyTask()

    suspend fun addTask(
        title: String,
        description: String,
        subject: Subject,
        priority: Priority,
        dueDate: String
    ) {
        taskDao.insertTask(
            TaskEntity(
                title = title,
                description = description,
                subject = subject.name,
                priority = priority.name,
                dueDate = dueDate
            )
        )
    }

    suspend fun updateTask(task: StudyTask) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun deleteTask(id: Int) {
        taskDao.deleteTaskById(id)
    }

    suspend fun toggleCompleted(id: Int) {
        val task = taskDao.getTaskById(id) ?: return
        taskDao.updateTask(task.copy(isCompleted = !task.isCompleted))
    }
}