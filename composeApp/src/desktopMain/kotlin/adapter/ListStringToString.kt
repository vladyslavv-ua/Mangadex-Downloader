package adapter

import app.cash.sqldelight.ColumnAdapter

class ListStringToString: ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String): List<String> {

        val tmp = databaseValue.removeSurrounding("[", "]").split(", ")
        return tmp.map {
            it.replace("#\\,\\#", ",")
        }

    }

    override fun encode(value: List<String>): String {
        value.map {
            var res = it
            if (it.contains(",")){
                res = it.replace(",", "#\\,\\#")
            }
            res
        }
        return value.toString()
    }
}