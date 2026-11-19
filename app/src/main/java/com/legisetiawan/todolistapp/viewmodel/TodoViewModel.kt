package com.legisetiawan.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legisetiawan.todolistapp.data.TodoRepository
import com.legisetiawan.todolistapp.data.local.TodoDatabase
import com.legisetiawan.todolistapp.data.local.TodoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Inisialisasi Repository (Jembatan ke Database)
    private val repository: TodoRepository = TodoRepository(
        TodoDatabase.getDatabase(application).todoDao()
    )

    // 2. State untuk daftar semua Todo (Menggunakan stateIn agar lebih efisien & modern)
    val todos: StateFlow<List<TodoEntity>> = repository.allTodos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    // 3. State untuk Todo yang sedang diedit/dilihat (Private Mutable, Public Immutable)
    private val _currentTodo = MutableStateFlow<TodoEntity?>(null)
    val currentTodo: StateFlow<TodoEntity?> = _currentTodo

    // ==========================================
    // FUNGSI-FUNGSI (ACTIONS)
    // ==========================================

    // Memuat data todo berdasarkan ID (digunakan saat masuk ke mode Edit)
    fun loadTodo(id: Int) {
        viewModelScope.launch {
            repository.getTodoById(id).collect { todo ->
                _currentTodo.value = todo
            }
        }
    }

    // Menyimpan Todo (Bisa untuk Tambah Baru atau Update)
    fun saveTodo(title: String, description: String) {
        viewModelScope.launch {
            val current = _currentTodo.value
            if (current != null) {
                // Mode Edit: Update data yang sudah ada
                repository.update(current.copy(title = title, description = description))
            } else {
                // Mode Tambah: Buat data baru
                repository.insert(TodoEntity(title = title, description = description))
            }
            // Reset current todo setelah menyimpan agar form bersih kembali
            _currentTodo.value = null
        }
    }

    // Menghapus Todo
    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    // Mengubah status completed (mencentang tugas)
    fun toggleComplete(todo: TodoEntity) {
        viewModelScope.launch {
            repository.update(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    // Membersihkan state current todo (misal: user batal edit dan kembali ke list)
    fun clearCurrentTodo() {
        _currentTodo.value = null
    }
}