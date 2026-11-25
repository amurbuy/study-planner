package com.example.studyplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studyplanner.model.Subject
import com.example.studyplanner.screens.AddEditTaskScreen
import com.example.studyplanner.screens.BooksScreen
import com.example.studyplanner.screens.SettingsScreen
import com.example.studyplanner.screens.StatsScreen
import com.example.studyplanner.screens.TaskDetailScreen
import com.example.studyplanner.screens.TaskListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "task_list") {

        composable("task_list") {
            TaskListScreen(
                onTaskClick = { taskId -> navController.navigate("task_detail/$taskId") },
                onAddClick = { navController.navigate("add_task") },
                onStatsClick = { navController.navigate("stats") },
                onBooksClick = { navController.navigate("books") },
                onSettingsClick = { navController.navigate("settings") }
            )
        }

        composable("add_task") {
            AddEditTaskScreen(
                taskId = null,
                onBack = { navController.navigateUp() }
            )
        }

        composable(
            route = "task_detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskDetailScreen(
                taskId = taskId,
                onBack = { navController.popBackStack() },
                onEditClick = { id -> navController.navigate("edit_task/$id") },
                onFindBooksClick = { subject -> navController.navigate("books/${subject.name}") }
            )
        }

        composable(
            route = "edit_task/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            AddEditTaskScreen(
                taskId = taskId,
                onBack = { navController.navigateUp() }
            )
        }

        composable("stats") {
            StatsScreen(onBack = { navController.navigateUp() })
        }

        composable("settings") {
            SettingsScreen(onBack = { navController.navigateUp() })
        }

        composable("books") {
            BooksScreen(
                initialSubject = Subject.MATH,
                onBack = { navController.navigateUp() }
            )
        }

        composable(
            route = "books/{subject}",
            arguments = listOf(navArgument("subject") { type = NavType.StringType })
        ) { backStackEntry ->
            val subjectName = backStackEntry.arguments?.getString("subject")
            val subject = Subject.entries.find { it.name == subjectName } ?: Subject.MATH
            BooksScreen(
                initialSubject = subject,
                onBack = { navController.navigateUp() }
            )
        }
    }
}