package su.tease.project.core.utils.ext


import org.junit.Assert.assertEquals
import org.junit.Test

class ListExtTest {

    @Test
    fun `2 items list`() {
        val list = listOf(1, 2)

        val halves = list.halves()

        assertEquals(Pair(listOf(1), listOf(2)), halves)
    }

    @Test
    fun `3 items list`() {
        val list = listOf(1, 2, 3)

        val halves = list.halves()

        assertEquals(Pair(listOf(1), listOf(2, 3)), halves)
    }

    @Test
    fun `4 items list`() {
        val list = listOf(1, 2, 3, 4)

        val halves = list.halves()

        assertEquals(Pair(listOf(1, 2), listOf(3, 4)), halves)
    }

    @Test
    fun `5 items list`() {
        val list = listOf(1, 2, 3, 4, 5)

        val halves = list.halves()

        assertEquals(Pair(listOf(1, 2), listOf(3, 4, 5)), halves)
    }

    @Test
    fun `6 items list`() {
        val list = listOf(1, 2, 3, 4, 5, 6)

        val halves = list.halves()

        assertEquals(Pair(listOf(1, 2, 3), listOf(4, 5, 6)), halves)
    }
}
