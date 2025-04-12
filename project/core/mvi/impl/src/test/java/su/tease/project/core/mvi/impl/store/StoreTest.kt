package su.tease.project.core.mvi.impl.store

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.mvi.impl.state.CombinedState
import su.tease.project.core.mvi.impl.store.utils.select
import su.tease.project.core.mvi.impl.store.stub.createStubStore
import su.tease.project.core.mvi.impl.store.stub.stub1.StubState1
import su.tease.project.core.mvi.impl.store.stub.stub2.StubAction2
import su.tease.project.core.mvi.impl.store.stub.stub2.StubState2
import su.tease.project.core.mvi.impl.store.stub.stub2.StubSuspendAction2

class StoreTest {

    @Test
    fun check_init_state() {
        val store = createStubStore()

        assertEquals(
            CombinedState(StubState1(), StubState2()),
            store.state.value
        )
    }

    @Test
    fun check_simple_action_state() = runTest {
        val store = createStubStore()
        val expectedIntValue = 42

        store.dispatcher.dispatch(StubAction2.OnUpdateIntValue(expectedIntValue))

        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(intValue = expectedIntValue)
            ),
            store.state.value
        )
    }

    @Test
    fun check_suspend_action_state() = runTest {
        val store = createStubStore()
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        store.dispatcher.dispatch(
            StubSuspendAction2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue,
            )
        )

        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(
                    intValue = expectedIntValue,
                    stringValue = expectedStringValue,
                    listStringValue = expectedListStringValue + expectedStringValue
                )
            ),
            store.state.value
        )
    }


    @Test
    fun check_suspend_action_and_subscription_state() = runTest {
        val store = createStubStore()
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val stateUpdates = mutableListOf<StubState2>()
        val job = launch { store.select<StubState2, StubState2> { it }.toList(stateUpdates) }
        store.dispatcher.dispatch(
            StubSuspendAction2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue,
            )
        )

        assertEquals(
            StubState2(),
            stateUpdates[0]
        )
        assertEquals(
            StubState2().copy(
                intValue = expectedIntValue,
            ),
            stateUpdates[1]
        )
        assertEquals(
            StubState2().copy(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
            ),
            stateUpdates[2]
        )
        assertEquals(
            StubState2().copy(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue
            ),
            stateUpdates[3]
        )
        assertEquals(
            StubState2().copy(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue + expectedStringValue
            ),
            stateUpdates[4]
        )
        job.cancel()
    }
}
