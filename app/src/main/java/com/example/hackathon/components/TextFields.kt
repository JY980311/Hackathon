package com.example.hackathon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun HackathonTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    singleLine : Boolean = true,
    onValueChange: (String) -> Unit
) {
    Column(
    ) {
        Text(text = label)
        Surface(
            color = Color.LightGray,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(7.dp)
        ) {
            BasicTextField(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 20.dp
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Email),
                value = value,
                onValueChange = onValueChange
            )
        }
    }

}



@Composable
fun HackathonPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    val passwordVisble = remember { mutableStateOf(false) }

    val passworVisbleIcon =
        if (passwordVisble.value) com.google.android.material.R.drawable.design_ic_visibility else com.google.android.material.R.drawable.design_ic_visibility_off

    Column() {
        Text(text = label)
        Surface(
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(7.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                BasicTextField(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        )
                        .weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisble.value) VisualTransformation.None else PasswordVisualTransformation(),
                    value = value,
                    onValueChange = onValueChange)
                IconButton(onClick = {
                    passwordVisble.value = !passwordVisble.value
                }) {
                    Image(
                        painter = painterResource(id = passworVisbleIcon),
                        contentDescription = "비밀번호 노출 여부",

                        )
                }
            }
        }
    }
}