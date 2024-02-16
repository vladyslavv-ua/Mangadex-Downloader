package di

import screenModel.MainSM
import org.koin.dsl.module
import screenModel.MangaSM
import screenModel.SelectChapterByLangSM

fun screenModel() = module {
    factory { MainSM(get()) }
    factory { (mangaId: String) -> MangaSM(get(), mangaId) }
    factory { (mangaId: String, lang: String)-> SelectChapterByLangSM(get(), mangaId, lang) }
}