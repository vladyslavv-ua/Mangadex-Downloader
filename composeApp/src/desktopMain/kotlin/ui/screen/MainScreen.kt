package ui.screen

import COVER_BASE_URL
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import screenModel.MainSM
import ui.component.MangaCard

class MainScreen : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<MainSM>()
        val searchField = screenModel.search.collectAsState()
        val mangaList = screenModel.mangaList.collectAsState()
        val windowInfo = LocalWindowInfo.current
        Scaffold { pv ->
            Surface(modifier = Modifier.fillMaxSize().padding(pv)) {
                Column(modifier = Modifier.padding(top = 25.dp, start = 25.dp, end = 25.dp, bottom = 0.dp)) {
                    Row(Modifier.height(50.dp)) {
                        OutlinedTextField(
                            searchField.value,
                            onValueChange = { screenModel.updateField(it) },
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        OutlinedButton(
                            onClick = { screenModel.searchManga() },
                            modifier = Modifier.width(100.dp).fillMaxHeight()
                        ) {
                            Text("Search")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 50.dp)
                    ) {
                        items(items = mangaList.value, key = { it.id }) {
                            MangaCard(
                                it.name,
                                COVER_BASE_URL + "${it.id}/${it.coverId}",
                                modifier = Modifier.clickable {
                                    navigator.push(MangaScreen(it.id))
                                })
                        }
                    }
                }
            }
        }
    }
}