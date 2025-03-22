package com.example.simpletodocusatteaching.model

import com.example.simpletodocusatteaching.ui.components.TodoItemModel

interface StorageServices {

    fun addListener(
        onDocumentEvent: (Boolean, TodoItemModel) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun removeListener()

    fun saveTodo(item: TodoItemModel, onResult: (Throwable?) -> Unit )
    fun updateTodo(item: TodoItemModel, onResult: (Throwable?) -> Unit )
    fun deleteTodo(item: TodoItemModel, onResult: (Throwable?) -> Unit )
    fun getTodos(items: List<TodoItemModel>, onResult: (Throwable?) -> Unit )
}