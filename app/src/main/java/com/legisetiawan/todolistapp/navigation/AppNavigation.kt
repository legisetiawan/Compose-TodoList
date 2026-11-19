package com.legisetiawan.todolistapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.legisetiawan.todolistapp.ui.screens.AddEditTodoScreen
import com.legisetiawan.todolistapp.ui.screens.TodoListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "todo_list") {
        composable("todo_list") {
            TodoListScreen(
                onAddTodoClick = { navController.navigate("add_edit_todo/-1") },
                onEditTodoClick = { todoId -> navController.navigate("add_edit_todo/$todoId") }
            )
        }
        composable(
            route = "add_edit_todo/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
            AddEditTodoScreen(
                todoId = todoId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}