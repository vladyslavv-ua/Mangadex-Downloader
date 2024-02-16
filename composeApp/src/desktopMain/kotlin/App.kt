import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.screenModel
import di.singleton
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import ui.screen.MainScreen

@Composable
fun App() {

    startKoin {
        logger(PrintLogger(Level.INFO))
        modules(singleton() + screenModel())
    }


    Navigator(MainScreen())

}