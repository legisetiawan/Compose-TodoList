package com.legisetiawan.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legisetiawan.todolistapp.data.TodoRepository
import com.legisetiawan.todolistapp.data.local.TodoDatabase
import com.legisetiawan.todolistapp.data.local.TodoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository
    val todos: StateFlow<List<TodoEntity>>
    private val _currentTodo = MutableStateFlow<TodoEntity?>(null)
    val currentTodo: StateFlow<TodoEntity?> = _currentTodo

    init {
        val dao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(dao)

        // Mengumpulkan data dari database secara real-time
        val todosFlow = MutableStateFlow<List<TodoEntity>>(emptyList())
        viewModelScope.launch {
            repository.allTodos.collect { todosFlow.value = it }
        }
        todos = todosFlow
    }

    fun loadTodo(id: Int) {
        viewModelScope.launch {
            repository.getTodoById(id).collect { _currentTodo.value = it }
        }
    }

    fun saveTodo(title: String, description: String) {
        viewModelScope.launch {
            val current = _currentTodo.value
            if (current != null) {
                repository.update(current.copy(title = title, description = description))
            } else {
                repository.insert(TodoEntity(title = title, description = description))
            }
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    fun toggleComplete(todo: TodoEntity) {
        viewModelScope.launch {
            repository.update(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    fun resetCurrentTodo() {
        _currentTodo.value = null
    }
}