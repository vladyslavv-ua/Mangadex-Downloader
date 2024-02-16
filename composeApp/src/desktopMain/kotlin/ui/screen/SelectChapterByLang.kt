package ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import screenModel.SelectChapterByLangSM

class SelectChapterByLang(private val mangaId: String, private val lang: String) : Screen, KoinComponent {
    val screenModel: SelectChapterByLangSM = get(parameters = { parametersOf(mangaId, lang) })

    @Composable
    override fun Content() {
        println("navigated")
        val navigator = LocalNavigator.currentOrThrow
        val chapterByLangSM = screenModel.chapters.collectAsLazyPagingItems()
        Scaffold(topBar = {
            OutlinedButton(onClick = { navigator.pop() }) {
                Text("Back")
            }
        }) {
            Surface(modifier = Modifier.padding(it)) {
                LazyColumn(Modifier.fillMaxWidth(.5f).border(2.dp, Color.Green)) {
                    items(chapterByLangSM.itemCount) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                chapterByLangSM[it]?.attributes?.volume ?: "No TITLE"
                            )
                        }
                    }
                    item {
                        Text(chapterByLangSM.loadState.toString())
                        Text(chapterByLangSM.loadState.append.toString())
                        OutlinedButton(onClick = {
                            chapterByLangSM.refresh()
                        }){
                            Text("Load")
                        }
                    }
                }
            }
        }
    }
}