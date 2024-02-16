package adapter

import app.cash.sqldelight.ColumnAdapter

class ListMapStringStringToString : ColumnAdapter<List<Map<String, String>>, String> {
    override fun decode(databaseValue: String): List<Map<String, String>> {
        val list = mutableListOf<Map<String, String>>()
        val cleanedInput = databaseValue.removeSurrounding("[{", "}]")
        val records = cleanedInput.split("}, {")
        if (cleanedInput == "[]")
            return emptyList()

        records.forEach { record ->
            var (key, value) = record.split("=")
            val map = mutableMapOf<String, String>()

            key = key.replace("#\\{\\#", "{")
            key = key.replace("#\\}\\#", "}")
            key = key.replace("#\\e*q\\#", "=")
            value = value.replace("#\\{\\#", "{")
            value = value.replace("#\\}\\#", "}")
            value = value.replace("#\\e*q\\#", "=")

            map[key] = value
            list.add(map)
        }
        return list
    }

    override fun encode(value: List<Map<String, String>>): String {
        val a =  value.map {
            println(it)
            var key = it.keys.first()
            var value = it.values.first()
            key = key.replace("{", "#\\{\\#")
            key = key.replace("}", "#\\}\\#")
            key = key.replace("=", "#\\e*q\\#")
            value = value.replace("}", "#\\}\\#")
            value = value.replace("{", "#\\{\\#")
            value = value.replace("=", "#\\e*q\\#")

            mapOf(key to value)
        }
        return a.toString()
    }
}