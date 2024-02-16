package ui.screen

import COVER_BASE_URL
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import screenModel.MangaSM

class MangaScreen(private val mangaId: String) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: MangaSM = get(parameters = { parametersOf(mangaId) })
        val mangaInfo by screenModel.mangaScreenInfo.collectAsState()
        val loadingState by screenModel.loadingState.collectAsState()
        Scaffold(topBar = {
            OutlinedButton(onClick = { navigator.pop() }) {
                Text("Back")
            }
        }) { pv ->
            if (!loadingState) {
                Surface(modifier = Modifier.padding(pv)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
                        Box(modifier = Modifier.width(300.dp)) {
                            AsyncImage(
                                COVER_BASE_URL + "$mangaId/${mangaInfo.cover}",
                                contentDescription = null,
                            )
                        }
                        Column {
                            Text(mangaInfo.name)
                            Text(mangaInfo.description)
                            Text(mangaInfo.status)
                            OutlinedButton(onClick = {

//                                DownloadScreenHost(mangaId, mangaInfo.availableTranslatedLanguages)
                                navigator.push(DownloadScreenHost(mangaId, mangaInfo.availableTranslatedLanguages))
                            }) {
                                Text("Download")
                            }
                            Text("Alt names")
                            LazyColumn {
                                items(mangaInfo.altTitles) {
                                    Row {
                                        Text(it.keys.first())
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(it.values.first())

                                    }
                                }
                            }

                        }
                    }


                }
            }

        }
    }
}