package ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage

@Composable
fun MangaCard(name: String, imageUrl: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(imageUrl, contentDescription = null)
        Text(name, textAlign = TextAlign.Center)
    }
}