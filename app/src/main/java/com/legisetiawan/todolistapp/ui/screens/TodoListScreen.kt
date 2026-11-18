package com.legisetiawan.todolistapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.legisetiawan.todolistapp.data.local.TodoEntity
import com.legisetiawan.todolistapp.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onAddTodoClick: () -> Unit,
    onEditTodoClick: (Int) -> Unit,
    viewModel: TodoViewModel = viewModel()
) {
    val todos by viewModel.todos.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("My To-Do List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTodoClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { paddingValues ->
        if (todos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Belum ada tugas. Yuk tambah!", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(todos, key = { it.id }) { todo ->
                    TodoItem(
                        todo = todo,
                        onEditClick = { onEditTodoClick(todo.id) },
                        onDeleteClick = { viewModel.deleteTodo(todo) },
                        onCheckChange = { viewModel.toggleComplete(todo) }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: TodoEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckChange: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = todo.isCompleted, onCheckedChange = { onCheckChange() })
            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text(text = todo.title, style = MaterialTheme.typography.titleMedium, textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null)
                if (todo.description.isNotEmpty()) {
                    Text(text = todo.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            TextButton(onClick = onEditClick) { Text("Edit") }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}