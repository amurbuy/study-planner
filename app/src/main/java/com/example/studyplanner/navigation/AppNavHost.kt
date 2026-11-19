package com.example.studyplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyplanner.screens.TaskListScreen
import com.example.studyplanner.screens.AddEditTaskScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.studyplanner.screens.TaskDetailScreen



@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "task_list") {

        composable("task_list") {
            TaskListScreen(
                onTaskClick = { taskId -> navController.navigate("task_detail/$taskId") },
                onAddClick = { navController.navigate("add_task") }
            )
        }
        composable("add_task") {
            AddEditTaskScreen(
                taskId = null,
                onBack = { navController.popBackStack() }
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
                onEditClick = { }
            )
        }

    }
}
