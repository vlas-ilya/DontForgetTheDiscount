package su.tease.project.core.utils.stack

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import su.tease.project.core.utils.stack.stub.toStubIntList

class StackRemoveAllTest {
    @Test
    fun `Remove all elements`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.removeAll { true }

        assertNull(newStack)
    }

    @Test
    fun `Remove one element`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.removeAll { it.value == 2 }

        assertEquals(listOf(1, 3, 4, 5).toStubIntList().toStack(), newStack)
    }

    @Test
    fun `Remove few elements`() {
        val stack = listOf(1, 2, 3, 4, 5).toStubIntList().toStack() ?: return

        val newStack = stack.removeAll { it.value % 2 == 0 }

        assertEquals(listOf(1, 3, 5).toStubIntList().toStack(), newStack)
    }
}
