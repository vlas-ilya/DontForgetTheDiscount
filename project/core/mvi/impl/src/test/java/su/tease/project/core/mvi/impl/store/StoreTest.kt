package su.tease.project.core.mvi.impl.store

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.mvi.impl.combine.CombinedState
import su.tease.project.core.mvi.impl.stub.createStubStore
import su.tease.project.core.mvi.impl.stub.stub1.StubState1
import su.tease.project.core.mvi.impl.stub.stub2.StubAction2
import su.tease.project.core.mvi.impl.stub.stub2.StubState2

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
        val expectedIntValue = 42

        val store = createStubStore()

        store.dispatcher.dispatch(StubAction2.OnUpdateIntValue(expectedIntValue)).join()

        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(intValue = expectedIntValue)
            ),
            store.state.value
        )
    }

    @Test
    fun `Check subscription`() = runTest {
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val store = createStubStore()
        val data = store.state

        val stateUpdates = mutableListOf<CombinedState<StubState1, StubState2>>()
        val job = launch { data.toList(stateUpdates) }
        val dispatcher = store.dispatcher

        delay(100)
        dispatcher.dispatch(StubAction2.OnUpdateIntValue(expectedIntValue)).join()
        delay(100)
        dispatcher.dispatch(StubAction2.OnUpdateStringValue(expectedStringValue)).join()
        delay(100)
        dispatcher.dispatch(StubAction2.OnUpdateListStringValue(expectedListStringValue)).join()
        delay(100)
        dispatcher.dispatch(StubAction2.OnAddToListStringValue(expectedStringValue)).join()
        delay(100)

        assertEquals(
            CombinedState(
                StubState1(),
                StubState2(),
            ),
            stateUpdates[0]
        )
        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(
                    intValue = expectedIntValue
                ),
            ),
            stateUpdates[1]
        )
        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(
                    intValue = expectedIntValue,
                    stringValue = expectedStringValue,
                ),
            ),
            stateUpdates[2]
        )
        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(
                    intValue = expectedIntValue,
                    stringValue = expectedStringValue,
                    listStringValue = expectedListStringValue
                ),
            ),
            stateUpdates[3]
        )
        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(
                    intValue = expectedIntValue,
                    stringValue = expectedStringValue,
                    listStringValue = expectedListStringValue + expectedStringValue
                ),
            ),
            stateUpdates[4]
        )
        assertEquals(
            CombinedState(
                StubState1(),
                StubState2().copy(
                    intValue = expectedIntValue,
                    stringValue = expectedStringValue,
                    listStringValue = expectedListStringValue + expectedStringValue
                ),
            ),
            store.state.value
        )

        job.cancel()
    }
}
