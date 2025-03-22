package com.example.simpletodocusatteaching.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodocusatteaching.model.HomeScreenUIStates
import com.example.simpletodocusatteaching.model.StorageServicesImpl
import com.example.simpletodocusatteaching.ui.components.TodoItemModel
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TodoHomeScreenViewModel"

@HiltViewModel
class TodoHomeScreenViewModel @Inject constructor(
    val reference: CollectionReference
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUIStates> =
        MutableStateFlow(HomeScreenUIStates())
    val uiState = _uiState.asStateFlow()

    val storageServices = StorageServicesImpl(reference)

    val collection = mutableStateMapOf<String,TodoItemModel>()

    fun addListener() = viewModelScope.launch {
        storageServices.addListener(
            onError = {
                Log.d(TAG, "addListener: Something went wrong")
            },
            onDocumentEvent = { wasDeleted, todo ->
                Log.d(TAG, "addListener: onDocumentEvent: $wasDeleted , $todo")
                if (wasDeleted) {
                    collection.remove(todo.id)
                } else {
                    collection[todo.id] = todo
                }
            }
        )
    }

    fun removeListener() = viewModelScope.launch {
        storageServices.removeListener()
    }

    fun saveToFireStore(item: TodoItemModel) = viewModelScope.launch {
        storageServices.saveTodo(item, onResult = { error ->
            _uiState.update {
                it.copy(
                    toastMessage = if (error?.message.isNullOrEmpty()) "Todo saved successfully" else error.message.orEmpty()
                )
            }
        })
    }

    fun updateTodo(item: TodoItemModel) = viewModelScope.launch {
        Log.d(TAG, "updateTodo: $item")
        storageServices.updateTodo(item){ error ->
            _uiState.update {
                it.copy(
                    toastMessage = if (error?.message.isNullOrEmpty()) "Todo updated successfully" else error?.message.orEmpty()
                )
            }
        }
    }

    fun removeTodo(item: TodoItemModel) = viewModelScope.launch {
        storageServices.deleteTodo(item) { error ->
            _uiState.update {
                it.copy(
                    toastMessage = if (error?.message.isNullOrEmpty()) "Todo removed successfully" else error?.message.orEmpty()
                )
            }
        }
    }
}