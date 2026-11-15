package com.example.studyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.data.TaskRepository
import com.example.studyplanner.model.StudyTask
import com.example.studyplanner.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class TaskListUiState(
    val tasks: List<StudyTask> = emptyList(),
    val selectedSubject: Subject? = null
)

class TaskListViewModel : ViewModel() {

    private val repository = TaskRepository
    private val selectedSubject = MutableStateFlow<Subject?>(null)

    val uiState: StateFlow<TaskListUiState> =
        combine(repository.tasks, selectedSubject) { tasks, subject ->
            val filtered = if (subject == null) tasks else tasks.filter { it.subject == subject }
            TaskListUiState(
                tasks = filtered.sortedBy { it.isCompleted },
                selectedSubject = subject
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TaskListUiState()
        )

    fun onSubjectSelected(subject: Subject?) {
        selectedSubject.value = subject
    }

    fun onToggleCompleted(taskId: Int) {
        repository.toggleCompleted(taskId)
    }
}