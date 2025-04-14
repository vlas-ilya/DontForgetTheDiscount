package su.tease.project.core.utils.stack

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import su.tease.project.core.utils.stack.stub.toStubIntList

class StackRemoveLastTest {

    @Test
    fun `Remove single element`() {
        val stack = listOf(1).toStubIntList().toStack() ?: return

        val newStack = stack.removeLast()

        assertNull(newStack)
    }

    @Test
    fun `Remove some element`() {
        val stack = listOf(1, 2, 3, 4).toStubIntList().toStack() ?: return

        val newStack = stack.removeLast()

        assertEquals(listOf(1, 2, 3).toStubIntList().toStack(), newStack)
    }
}
