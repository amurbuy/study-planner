package com.example.studyplanner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyplanner.components.AppTopBar
import com.example.studyplanner.components.EmptyState
import com.example.studyplanner.components.PriorityChip
import com.example.studyplanner.components.SubjectChip
import com.example.studyplanner.viewmodel.TaskDetailViewModel

@Composable
fun TaskDetailScreen(
    taskId: Int,
    onBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: TaskDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    val task = uiState.task

    Scaffold(
        topBar = { AppTopBar(title = "Task details", onBack = onBack) }
    ) { innerPadding ->
        if (task == null) {
            EmptyState(
                title = "Task not found",
                message = "This task may have been deleted",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SubjectChip(task.subject)
                    PriorityChip(task.priority)
                }

                DetailItem(label = "Due date", value = task.dueDate)
                DetailItem(
                    label = "Status",
                    value = if (task.isCompleted) "Completed" else "Not completed"
                )
                DetailItem(
                    label = "Description",
                    value = task.description.ifBlank { "No description" }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.toggleCompleted() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (task.isCompleted) "Mark as not completed" else "Mark as completed")
                }

                OutlinedButton(
                    onClick = { onEditClick(task.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit")
                }

                OutlinedButton(
                    onClick = {
                        viewModel.deleteTask()
                        onBack()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete")
                }
            }
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
