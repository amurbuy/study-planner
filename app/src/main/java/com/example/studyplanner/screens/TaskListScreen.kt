package com.example.studyplanner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyplanner.components.AppTopBar
import com.example.studyplanner.components.EmptyState
import com.example.studyplanner.components.SubjectFilterRow
import com.example.studyplanner.components.TaskCard
import com.example.studyplanner.viewmodel.TaskListViewModel

@Composable
fun TaskListScreen(
    onTaskClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onStatsClick: () -> Unit,
    onBooksClick: () -> Unit,
    viewModel: TaskListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Study Planner",
                actions = {
                    TextButton(onClick = onBooksClick) { Text("Books") }
                    TextButton(onClick = onStatsClick) { Text("Stats") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, contentDescription = "Add task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SubjectFilterRow(
                selectedSubject = uiState.selectedSubject,
                onSubjectSelected = { viewModel.onSubjectSelected(it) },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (uiState.tasks.isEmpty()) {
                EmptyState(
                    title = if (uiState.selectedSubject == null) "No tasks yet" else "No tasks for this subject",
                    message = "Tap + to add a new study task"
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.tasks, key = { it.id }) { task ->
                        TaskCard(
                            task = task,
                            onClick = { onTaskClick(task.id) },
                            onToggleCompleted = { viewModel.onToggleCompleted(task.id) }
                        )
                    }
                }
            }
        }
    }
}