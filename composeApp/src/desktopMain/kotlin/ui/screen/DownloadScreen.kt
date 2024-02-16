package ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.core.component.KoinComponent

class DownloadScreen(private val mangaId: String, private val availableLangs: List<String>) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(topBar = {
            OutlinedButton(onClick = { navigator.pop() }) {
                Text("Back")
            }
        }) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues).padding(20.dp, 10.dp)) {
                items(availableLangs) { lang ->
                    OutlinedButton( onClick = {navigator.push(SelectChapterByLang(mangaId, lang))
                    }) {
                        Text(lang)
                    }

                }

            }
        }
    }
}


class DownloadScreenHost(private val mangaId: String, private val availableLangs: List<String>) : Screen,
    KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Navigator(DownloadScreen(mangaId, availableLangs), onBackPressed = {
            println(it)
            true
        }) {
            println(it.parent)
            SlideTransition(it)
        }
    }
}

//@Composable
//fun DownloadScreenHost(mangaId: String, availableLangs: List<String>){
//    Navigator(DownloadScreen(mangaId, availableLangs)) {
//        println(it.parent)
//        SlideTransition(it)
//    }
//}