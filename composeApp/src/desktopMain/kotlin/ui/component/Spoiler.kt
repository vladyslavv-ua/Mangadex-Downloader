package ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun Spoiler(
    spoilerHeader: String,
    headerStyles: Modifier = Modifier,
    styles: Modifier = Modifier,
    onExpand: () -> Unit,
    content: @Composable () -> Unit
) {
    val visible = remember {
        mutableStateOf(false)
    }

    Column(modifier = styles) {

        Text(spoilerHeader, modifier = headerStyles
            .fillMaxWidth()
            .clickable {
                visible.value = !visible.value
                if (visible.value) {
                    onExpand()
                }
            }
        )
        AnimatedVisibility(visible = visible.value) {
            Column {
                content()
            }
        }
    }
}


@Composable
fun Spoiler(
    visible: Boolean,
    spoilerHeader: String,
    headerStyles: Modifier = Modifier,
    styles: Modifier = Modifier,
    onAction: () -> Unit,
    content: @Composable () -> Unit
) {

    Column(modifier = styles) {

        Text(spoilerHeader, modifier = headerStyles
            .fillMaxWidth()
            .clickable {
                    onAction()

            }
        )
        AnimatedVisibility(visible = visible) {
            Column {
                content()
            }
        }
    }
}