package com.example.studyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.data.TaskRepository
import com.example.studyplanner.model.Priority
import com.example.studyplanner.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddEditUiState(
    val taskId: Int? = null,
    val title: String = "",
    val description: String = "",
    val subject: Subject = Subject.MATH,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: String = "",
    val titleError: String? = null,
    val dueDateError: String? = null,
    val isSaved: Boolean = false
) {
    val isEditMode: Boolean get() = taskId != null
}

class AddEditTaskViewModel : ViewModel() {

    private val repository = TaskRepository

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState.asStateFlow()

    fun loadTask(taskId: Int) {
        if (_uiState.value.taskId == taskId) return
        viewModelScope.launch {
            val task = repository.getTaskById(taskId) ?: return@launch
            _uiState.value = AddEditUiState(
                taskId = task.id,
                title = task.title,
                description = task.description,
                subject = task.subject,
                priority = task.priority,
                dueDate = task.dueDate
            )
        }
    }

    fun onTitleChange(value: String) =
        _uiState.update { it.copy(title = value, titleError = null) }

    fun onDescriptionChange(value: String) =
        _uiState.update { it.copy(description = value) }

    fun onSubjectChange(value: Subject) =
        _uiState.update { it.copy(subject = value) }

    fun onPriorityChange(value: Priority) =
        _uiState.update { it.copy(priority = value) }

    fun onDueDateChange(value: String) =
        _uiState.update { it.copy(dueDate = value, dueDateError = null) }

    fun saveTask(onSaved: () -> Unit) {
        val state = _uiState.value

        val titleError = if (state.title.isBlank()) "Title cannot be empty" else null
        val dueDateError = if (state.dueDate.isBlank()) "Please enter a due date" else null

        if (titleError != null || dueDateError != null) {
            _uiState.update { it.copy(titleError = titleError, dueDateError = dueDateError) }
            return
        }

        // Stop a double tap on Save from adding the task twice
        if (state.isSaved) return
        _uiState.update { it.copy(isSaved = true) }

        viewModelScope.launch {
            if (state.taskId == null) {
                repository.addTask(
                    title = state.title.trim(),
                    description = state.description.trim(),
                    subject = state.subject,
                    priority = state.priority,
                    dueDate = state.dueDate.trim()
                )
            } else {
                repository.getTaskById(state.taskId)?.let { existing ->
                    repository.updateTask(
                        existing.copy(
                            title = state.title.trim(),
                            description = state.description.trim(),
                            subject = state.subject,
                            priority = state.priority,
                            dueDate = state.dueDate.trim()
                        )
                    )
                }
            }
            onSaved()
        }
    }
}