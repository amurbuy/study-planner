package com.example.studyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.data.TaskRepository
import com.example.studyplanner.model.Subject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class SubjectProgress(
    val subject: Subject,
    val completed: Int,
    val total: Int
) {
    val progress: Float get() = if (total == 0) 0f else completed.toFloat() / total
}

data class StatsUiState(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val subjectProgress: List<SubjectProgress> = emptyList()
) {
    val overallProgress: Float
        get() = if (totalTasks == 0) 0f else completedTasks.toFloat() / totalTasks
}

class StatsViewModel : ViewModel() {

    val uiState: StateFlow<StatsUiState> =
        TaskRepository.tasks.map { tasks ->
            StatsUiState(
                totalTasks = tasks.size,
                completedTasks = tasks.count { it.isCompleted },
                subjectProgress = tasks
                    .groupBy { it.subject }
                    .map { (subject, list) ->
                        SubjectProgress(
                            subject = subject,
                            completed = list.count { it.isCompleted },
                            total = list.size
                        )
                    }
                    .sortedBy { it.subject.ordinal }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StatsUiState()
        )
}