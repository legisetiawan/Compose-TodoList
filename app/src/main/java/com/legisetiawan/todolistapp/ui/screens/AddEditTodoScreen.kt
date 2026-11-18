package com.legisetiawan.todolistapp.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.legisetiawan.todolistapp.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    todoId: Int,
    onNavigateBack: () -> Unit,
    viewModel: TodoViewModel = viewModel()
) {
    val currentTodo by viewModel.currentTodo.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(todoId) {
        if (todoId != -1) viewModel.loadTodo(todoId) else viewModel.resetCurrentTodo()
    }

    LaunchedEffect(currentTodo) {
        currentTodo?.let {
            title = it.title
            description = it.description
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (todoId == -1) "Tambah Tugas" else "Edit Tugas") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Tugas") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Deskripsi") }, modifier = Modifier.fillMaxWidth().height(150.dp), maxLines = 5)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                if (title.isNotBlank()) {
                    viewModel.saveTodo(title.trim(), description.trim())
                    onNavigateBack()
                }
            }, modifier = Modifier.fillMaxWidth(), enabled = title.isNotBlank()) {
                Text("Simpan")
            }
        }
    }
}