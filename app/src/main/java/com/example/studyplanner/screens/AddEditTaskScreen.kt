package com.example.studyplanner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyplanner.components.OptionSelector
import com.example.studyplanner.model.Priority
import com.example.studyplanner.model.Subject
import com.example.studyplanner.viewmodel.AddEditTaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Int?,
    onBack: () -> Unit,
    viewModel: AddEditTaskViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(taskId) {
        if (taskId != null) viewModel.loadTask(taskId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (uiState.isEditMode) "Edit Task" else "New Task") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Title") },
                singleLine = true,
                isError = uiState.titleError != null,
                supportingText = { uiState.titleError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Description") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.dueDate,
                onValueChange = { viewModel.onDueDateChange(it) },
                label = { Text("Due date") },
                placeholder = { Text("e.g. 25 Nov 2026") },
                singleLine = true,
                isError = uiState.dueDateError != null,
                supportingText = { uiState.dueDateError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Subject", style = MaterialTheme.typography.titleSmall)
            OptionSelector(
                options = Subject.entries,
                selected = uiState.subject,
                optionLabel = { it.displayName },
                onOptionSelected = { viewModel.onSubjectChange(it) }
            )

            Text("Priority", style = MaterialTheme.typography.titleSmall)
            OptionSelector(
                options = Priority.entries,
                selected = uiState.priority,
                optionLabel = { it.displayName },
                onOptionSelected = { viewModel.onPriorityChange(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.saveTask()
                    if (viewModel.uiState.value.isSaved) onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditMode) "Save changes" else "Add task")
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}