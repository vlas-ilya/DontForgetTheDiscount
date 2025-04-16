package su.tease.project.core.utils.stack

import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.utils.stack.stub.StubInt
import su.tease.project.core.utils.stack.stub.toStubIntList

class StackAddTest {
    @Test
    fun `Add to Stack with one element`() {
        val stack = listOf(1).toStubIntList().toStack() ?: return

        val newStack = stack.add(StubInt(2))

        assertEquals(listOf(1, 2).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Add to Stack with few elements`() {
        val stack = listOf(1, 2, 3, 4).toStubIntList().toStack() ?: return

        val newStack = stack.add(StubInt(5))

        assertEquals(listOf(1, 2, 3, 4, 5).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Add existed element to Stack with few elements`() {
        val stack = listOf(1, 2, 3, 4).toStubIntList().toStack() ?: return

        val newStack = stack.add(StubInt(4))

        assertEquals(listOf(1, 2, 3, 4, 4).toStubIntList().toStack(), newStack)
    }
}
