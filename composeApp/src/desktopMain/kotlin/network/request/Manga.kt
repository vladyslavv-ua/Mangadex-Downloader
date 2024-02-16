package network.request

import io.ktor.resources.*

@Resource("manga")
class Manga {
    @Resource("")
    class Search(val parent: Manga = Manga(), val title: String)

    @Resource("{id}")
    class GetById(val parent: Manga = Manga(), val id: String){
        @Resource("aggregate")
        class Aggregate(val parent: GetById)
    }
}

