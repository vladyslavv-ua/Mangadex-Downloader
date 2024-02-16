package adapter

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class AdapterTest {

    class ListMapStringStringToStringTest {
        private lateinit var testingListMapStringStringToString: ListMapStringStringToString

        @Before
        fun setup(){
            testingListMapStringStringToString = ListMapStringStringToString()
        }

        @Test
        fun `this test tests basic behaviour`() {
            val excepted = listOf(mapOf(Pair("A", "b")))
            val result = testingListMapStringStringToString.decode(excepted.toString())
            assertEquals(excepted, result)
        }

        @Test
        fun `this test tests behaviour when some elements has a { or }`() {
            val excepted = listOf(mapOf(Pair("A{ }, {", "b{")))
            val result = testingListMapStringStringToString.decode("[{A#\\{\\# #\\}\\#, #\\{\\#=b#\\{\\#}]")
            assertEquals(excepted, result)

        }

        @Test
        fun `this test tests behaviour when some elements has a { or } or ,`() {
            val excepted = listOf(mapOf(Pair("A{ }, {", "b=}, {")))
            val exceptedString = testingListMapStringStringToString.encode(excepted)
            val result = testingListMapStringStringToString.decode(exceptedString)
            assertEquals(excepted, result)

        }

        @Test
        fun `this test tests behaviour when we have empty array`() {
            val excepted: List<Map<String, String>> = emptyList()
            val result = testingListMapStringStringToString.decode(excepted.toString())
            assertEquals(excepted, result)
        }

    }
}