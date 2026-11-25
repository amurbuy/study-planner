package com.example.studyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyplanner.data.SettingsRepository
import com.example.studyplanner.data.TaskRepository
import com.example.studyplanner.navigation.AppNavHost
import com.example.studyplanner.ui.theme.StudyPlannerTheme
import com.example.studyplanner.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskRepository.init(applicationContext)
        SettingsRepository.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val darkTheme by settingsViewModel.darkTheme.collectAsState()

            StudyPlannerTheme(darkTheme = darkTheme) {
                AppNavHost()
            }
        }
    }
}