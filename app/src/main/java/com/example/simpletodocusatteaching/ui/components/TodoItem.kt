package com.example.simpletodocusatteaching.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletodocusatteaching.ui.theme.SimpleTODOCusatTeachingTheme
import com.example.simpletodocusatteaching.ui.theme.black
import com.example.simpletodocusatteaching.ui.theme.grey400
import com.example.simpletodocusatteaching.ui.theme.grey500

data class TodoItemModel(
    val id: String = "",
    val text: String = "",
    val time: String = "",
    val completed: Boolean = false
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItemCollection(
    modifier: Modifier = Modifier,
    collection: List<TodoItemModel>,
    onRemove: (TodoItemModel) -> Unit,
    onItemClick: (item: TodoItemModel) -> Unit,
    addTodo: () -> Unit
) {

    if (collection.isEmpty()) {
        NoTodoFoundBox(
            modifier = Modifier.padding(16.dp),
            onClick = addTodo
        )
    } else {
        LazyColumn(
            modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            stickyHeader {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    text = "Tasks",
                    color = black,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            items(
                items = collection,
                key = { item -> item.id }
            ) { item ->
                TodoItem(
                    modifier = Modifier.fillMaxWidth(),
                    item = item,
                    onCheckedChange = {
                        onItemClick(item.copy(completed = it))
                    },
                    onRemove = onRemove
                )
            }
        }
    }
}


@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    item: TodoItemModel,
    onRemove: (item: TodoItemModel) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    val density = LocalDensity.current
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { with(density) { 24.dp.toPx() } },
        confirmValueChange = {
            when(it) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onRemove(item)
                }
                else -> false
            }

            return@rememberSwipeToDismissBoxState true
        }
    )
    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (swipeToDismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    else -> Color.White
                }
            )
            Box (Modifier
                .fillMaxSize()
                .background(color)
            ){
                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    tint = black
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(0.dp, color = Color.White),
            shape = RectangleShape
        ) {
            Row(
                modifier.clickable(role = Role.Checkbox) {
                    onCheckedChange(!item.completed)
                },
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                verticalAlignment = Alignment.Top
            ) {
//        if (isEditable) {
                Checkbox(
                    checked = item.completed,
                    onCheckedChange = onCheckedChange,
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color(0xFFFAFAFA),
                        checkedColor = black,
                        uncheckedColor = grey400
                    )
                )
//        } else {
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .clip(CircleShape)
//                    .background(color = black, shape = CircleShape)
//            )
//        }
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (item.completed) grey400 else grey500,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textDecoration = if (item.completed) TextDecoration.LineThrough else TextDecoration.None
                    )

                    Text(
                        text = item.time,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (item.completed) grey400 else grey500,
                        textDecoration = if (item.completed) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun TodoItemCollectionPrev() {
    SimpleTODOCusatTeachingTheme {
        TodoItemCollection(
            modifier = Modifier,
            collection = (1..5).map {
                TodoItemModel(
                    text = "Todfjaskljfldasl fjdslalfklflsdjdfjldasfalsfaljf l;jl  jlfdsakjlkf $it",
                    time = "12: 45 AM",
                    completed = it == 1,
                    id = System.currentTimeMillis().toString()
                )
            },
            onItemClick = { item ->

            },
            onRemove = {},
            addTodo = {}
        )
    }
}