package com.example.simpletodocusatteaching.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simpletodocusatteaching.ui.theme.black
import com.example.simpletodocusatteaching.ui.theme.grey400
import com.example.simpletodocusatteaching.ui.theme.label_light_secondary
import org.w3c.dom.Text
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun NoTodoFoundBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "No To-Do Found!",
            style = MaterialTheme.typography.displayLarge,
            color = black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "You have no tasks added yet. Tap the Add button to create a new to-do and stay organized!",
            style = MaterialTheme.typography.labelSmall,
            color = grey400,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth(fraction = 0.75f),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = black,
                contentColor = Color(0xFFFFFFFF)
            ),
            shape = RoundedCornerShape(12.dp),
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        tint = Color(0xFFFFFFFF),
                        contentDescription = ""
                    )
                    Text(
                        "Add Todo",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
    }
}