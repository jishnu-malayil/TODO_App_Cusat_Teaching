package com.example.simpletodocusatteaching.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletodocusatteaching.ui.components.TodoItemCollection
import com.example.simpletodocusatteaching.ui.sheets.AddTodoSheet
import com.example.simpletodocusatteaching.ui.theme.black
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TodoHomeScreen(
    viewModel: TodoHomeScreenViewModel = hiltViewModel<TodoHomeScreenViewModel>()
) {

    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage.isNotEmpty()) {
            Toast.makeText(context, uiState.toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    DisposableEffect(Unit) {
        viewModel.addListener()

        onDispose {
            viewModel.removeListener()
        }
    }

    var sheetVisible by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val openSheet: () -> Unit = {
        sheetVisible = true
        scope.launch { state.show() }
    }

    val closeSheet: () -> Unit = {
        scope.launch { state.hide() }
            .invokeOnCompletion {
                sheetVisible = false
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = openSheet,
                shape = CircleShape,
                containerColor = black,
                contentColor = Color(0xFFFFFFFF),
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->

        TodoItemCollection(
            modifier = Modifier
                .padding(paddingValues),
            collection = viewModel.collection.values.toList(),
            onItemClick = { item ->
                viewModel.apply {
                    updateTodo(item)
                }
            },
            onRemove = {
                viewModel.removeTodo(it)
            },
            addTodo = openSheet
        )
    }


    if (sheetVisible) {
        ModalBottomSheet(
            onDismissRequest = closeSheet,
            sheetState = state
        ) {
            AddTodoSheet(
                modifier = Modifier.padding(16.dp),
                onDone = {
                    Log.d("TAG", "TodoHomeScreen: $it")
                    closeSheet()
                    viewModel.saveToFireStore(item = it)
                }
            )
        }
    }

}