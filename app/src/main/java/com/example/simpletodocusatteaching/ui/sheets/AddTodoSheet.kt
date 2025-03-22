package com.example.simpletodocusatteaching.ui.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletodocusatteaching.ui.components.TodoItemModel
import com.example.simpletodocusatteaching.ui.theme.SimpleTODOCusatTeachingTheme
import com.example.simpletodocusatteaching.ui.theme.black
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddTodoSheet(
    modifier: Modifier = Modifier,
    onDone: (item: TodoItemModel) -> Unit
) {

    var taskName by remember { mutableStateOf("") }

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text(
            "Add a task",
            color = black,
            style = MaterialTheme.typography.displayMedium
        )

        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Name:",
                modifier = Modifier,
                color = black,
                style = MaterialTheme.typography.displayLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = taskName,
                onValueChange = {
                    taskName = it
                }
            )
        }

        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth(fraction = 0.75f),
            onClick = {
                onDone(
                    TodoItemModel(
                        text = taskName,
                        time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")),
                        completed = false,
                        id = System.currentTimeMillis().toString()
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = black,
                contentColor = Color(0xFFFFFFFF)
            ),
            shape = RoundedCornerShape(12.dp),
            content = {
                Text(
                    "Done",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun AddTodoSheetPrev() {
    SimpleTODOCusatTeachingTheme {
        AddTodoSheet(
            onDone = {

            }
        )
    }
}