package su.tease.project.core.mvi.impl.middleware

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.mvi.impl.combine.CombinedState
import su.tease.project.core.mvi.impl.stub.createStubStore
import su.tease.project.core.mvi.impl.stub.stub1.StubState1
import su.tease.project.core.mvi.impl.stub.stub2.StubState2
import su.tease.project.core.mvi.impl.stub.stub2.StubSuspendAction2

class SuspendMiddlewareTest {

    @Test
    fun check_suspend_action_and_subscription_state_by_selector_with_mapping() = runTest {
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val store = createStubStore()
        val data = store.state

        val stateUpdates = mutableListOf<CombinedState<StubState1, StubState2>>()
        val job = launch { data.toList(stateUpdates) }
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