package com.example.simpletodocusatteaching.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    val text: String,
    val time: String,
    val isChecked: Boolean
)


@Composable
fun TodoItemCollection(
    modifier: Modifier = Modifier,
    collection: List<TodoItemModel>,
    isEditable: Boolean = false,
    onItemClick: (index: Int, state: Boolean) -> Unit
) {

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        collection.forEachIndexed { index, item ->
            TodoItem(
                item = item,
                isEditable = isEditable,
                onCheckedChange = {
                    onItemClick(index, it)
                }
            )
        }
    }
}


@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    item: TodoItemModel,
    isEditable: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier.clickable(role = Role.Checkbox) {
            if (isEditable) onCheckedChange(!item.isChecked)
        },
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalAlignment = Alignment.Top
    ) {
        if (isEditable) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color(0xFFFAFAFA),
                    checkedColor = black,
                    uncheckedColor = grey400
                )
            )
        } else {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color = black, shape = CircleShape)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Text(
                text = item.text,
                style = MaterialTheme.typography.labelMedium,
                color = if (item.isChecked) grey400 else grey500,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )

            Text(
                text = item.time,
                style = MaterialTheme.typography.labelMedium,
                color = if (item.isChecked) grey400 else grey500,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
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
                    isChecked = it ==1
                )
            },
            isEditable = true,
            onItemClick = { index, state ->

            }
        )
    }
}