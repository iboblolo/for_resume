package com.example.jetpackroom

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackroom.db.Todo
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TodoItem(
    todo: Todo,
    onClick: (todo: Todo) -> Unit = {}
) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.JAPAN)
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick(todo)
            }
    ) {
        Text(
            text = todo.title,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        Text(
            text = "Click if done",
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().background(Color(98, 0, 238)).padding(10.dp)
        )
        
        Text(
            text = "${stringResource(id = R.string.todo_created_at)} ${sdf.format(todo.created_at)}",
            fontSize = 12.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth().bottomBorder(1.dp, Color.DarkGray)
        )


    }
}
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx/2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)