package com.example.simpletodocusatteaching.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.simpletodocusatteaching.ui.components.TodoItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoHomeScreenViewModel @Inject constructor() : ViewModel() {

    val collection = mutableStateListOf<TodoItemModel>()

    init {
        collection.addAll((1..50).map {
            TodoItemModel(
                text = "Todfjaskljfldasl fjdslalfklflsdjdfjldasfalsfaljf l;jl  jlfdsakjlkf $it",
                time = "12: 45 AM",
                isChecked = it == 1
            )
        }
        )
    }
}