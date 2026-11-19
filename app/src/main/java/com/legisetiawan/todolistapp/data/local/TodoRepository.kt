package com.legisetiawan.todolistapp.data

import com.legisetiawan.todolistapp.data.local.TodoDao
import com.legisetiawan.todolistapp.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    val allTodos: Flow<List<TodoEntity>> = todoDao.getAllTodos()
    fun getTodoById(id: Int): Flow<TodoEntity?> = todoDao.getTodoById(id)
    suspend fun insert(todo: TodoEntity) = todoDao.insertTodo(todo)
    suspend fun update(todo: TodoEntity) = todoDao.updateTodo(todo)
    suspend fun delete(todo: TodoEntity) = todoDao.deleteTodo(todo)
}