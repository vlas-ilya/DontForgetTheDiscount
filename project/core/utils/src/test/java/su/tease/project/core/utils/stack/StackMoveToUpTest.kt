package su.tease.project.core.utils.stack

import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.utils.stack.stub.StubInt
import su.tease.project.core.utils.stack.stub.toStubIntList

class StackMoveToUpTest {

    @Test
    fun `move to up first element`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.moveToUp(StubInt(1))

        assertEquals(listOf(2, 3, 4, 5, 1).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `move to up middle element`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.moveToUp(StubInt(3))

        assertEquals(listOf(1, 2, 4, 5, 3).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `move to up last element`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.moveToUp(StubInt(5))

        assertEquals(listOf(1, 2, 3, 4, 5).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `move to up new element`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.moveToUp(StubInt(6))

        assertEquals(listOf(1, 2, 3, 4, 5, 6).toStubIntList().toStack(), newStack)
    }
}