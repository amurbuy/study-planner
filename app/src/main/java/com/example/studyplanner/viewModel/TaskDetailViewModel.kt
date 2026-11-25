package com.example.studyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.data.TaskRepository
import com.example.studyplanner.model.StudyTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TaskDetailUiState(
    val task: StudyTask? = null
)

class TaskDetailViewModel : ViewModel() {

    private val repository = TaskRepository
    private val taskId = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<TaskDetailUiState> =
        combine(repository.tasks, taskId) { tasks, id ->
            TaskDetailUiState(task = tasks.find { it.id == id })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TaskDetailUiState()
        )

    fun loadTask(id: Int) {
        taskId.value = id
    }

    fun toggleCompleted() {
        val id = taskId.value ?: return
        viewModelScope.launch {
            repository.toggleCompleted(id)
        }
    }

    fun deleteTask(onDeleted: () -> Unit) {
        val id = taskId.value ?: return
        viewModelScope.launch {
            repository.deleteTask(id)
            onDeleted()
        }
    }
}