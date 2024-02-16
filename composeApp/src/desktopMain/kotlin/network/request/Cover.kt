package network.request

import io.ktor.resources.*

@Resource("cover/{id}")
class Cover(val id: String)