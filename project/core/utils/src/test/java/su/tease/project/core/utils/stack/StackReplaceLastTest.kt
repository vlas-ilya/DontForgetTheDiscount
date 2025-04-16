package su.tease.project.core.utils.stack

import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.utils.stack.stub.StubInt
import su.tease.project.core.utils.stack.stub.toStubIntList

class StackReplaceLastTest {
    @Test
    fun `Replace last`() {
        val stack = listOf(1, 2, 3, 4).toStubIntList().toStack() ?: return

        val newStack = stack.replaceLast(StubInt(5))

        assertEquals(listOf(1, 2, 3, 5).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Replace last to null`() {
        val stack = listOf(1, 2, 3, 4).toStubIntList().toStack() ?: return

        val newStack = stack.replaceLast(null)

        assertEquals(listOf(1, 2, 3).toStubIntList().toStack(), newStack)
    }
}
