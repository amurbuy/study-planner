package com.example.studyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.studyplanner.screens.AddEditTaskScreen
import com.example.studyplanner.screens.TaskListScreen
import com.example.studyplanner.ui.theme.StudyPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyPlannerTheme {
                // Temporary screen switch until Navigation Compose is added
                var showAddScreen by remember { mutableStateOf(false) }

                if (showAddScreen) {
                    AddEditTaskScreen(
                        taskId = null,
                        onBack = { showAddScreen = false }
                    )
                } else {
                    TaskListScreen(
                        onTaskClick = { },
                        onAddClick = { showAddScreen = true }
                    )
                }
            }
        }
    }
}