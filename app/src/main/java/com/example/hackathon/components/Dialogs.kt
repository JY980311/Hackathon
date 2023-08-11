package com.example.hackathon.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class DialogAction {
    CLOSE, ACTION
}

@Composable
fun SimpleDialog(
    isLoading: Boolean,
    onDialogAction: (DialogAction) -> Unit,
) {
    AlertDialog(onDismissRequest = {
        onDialogAction(DialogAction.CLOSE)
    },
        title = { Text(text = "안내") },
        text = { Text(text = "해당 글을 삭제하시겠습니까?") },
        confirmButton = {
            Button(onClick = { onDialogAction(DialogAction.ACTION)}) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.scale(1f).size(20.dp)
                    )
                } else {
                    Text(text = "삭제")
                }
            }
        },
        dismissButton = {
            Button(onClick = {onDialogAction(DialogAction.CLOSE)}){
                Text(text = "닫기")
            }
        }
    )
}