package su.tease.project.core.utils.stack

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import su.tease.project.core.utils.stack.stub.StubInt
import su.tease.project.core.utils.stack.stub.toStubIntList

class StackDropLastWhileTest {

    @Test
    fun `Drop last zero elements`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.dropLastWhile { it != StubInt(5) }

        assertEquals(listOf(1, 2, 3, 4, 5).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Drop last one element`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.dropLastWhile { it != StubInt(4) }

        assertEquals(listOf(1, 2, 3, 4).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Drop last few elements`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.dropLastWhile { it != StubInt(3) }

        assertEquals(listOf(1, 2, 3).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Drop all last elements`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.dropLastWhile { it != StubInt(1) }

        assertEquals(listOf(1).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Drop all elements`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.dropLastWhile { it != StubInt(0) }

        assertNull(newStack)
    }
}
