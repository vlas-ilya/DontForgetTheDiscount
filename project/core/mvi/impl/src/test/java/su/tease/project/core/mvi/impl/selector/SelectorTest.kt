package su.tease.project.core.mvi.impl.selector

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.impl.stub.createStubStore
import su.tease.project.core.mvi.impl.stub.stub2.StubState2
import su.tease.project.core.mvi.impl.stub.stub2.StubState2VO
import su.tease.project.core.mvi.impl.stub.stub2.StubSuspendAction2
import su.tease.project.core.mvi.impl.stub.stub2.toVO

class SelectorTest {

    @Test
    fun check_suspend_action_and_subscription_state_by_simple_selector() = runTest {
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val store = createStubStore()
        val data = store.select(StubState2::intValue)

        val stateUpdates = mutableListOf<Int>()
        val job = launch { data.toList(stateUpdates) }
        store.dispatcher.dispatch(
            StubSuspendAction2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue,
            )
        )

        assertEquals(
            0,
            stateUpdates[0]
        )
        assertEquals(
            expectedIntValue,
            stateUpdates[1]
        )
        assertEquals(
            expectedIntValue,
            stateUpdates[2]
        )
        assertEquals(
            expectedIntValue,
            stateUpdates[3]
        )
        assertEquals(
            expectedIntValue,
            stateUpdates[4]
        )
        job.cancel()
    }


    @Test
    fun check_suspend_action_and_subscription_state_by_id_selector() = runTest {
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val store = createStubStore()
        val data = store.select<StubState2>()

        val stateUpdates = mutableListOf<StubState2>()
        val job = launch { data.toList(stateUpdates) }
        store.dispatcher.dispatch(
            StubSuspendAction2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue,
            )
        )

        assertEquals(
            StubState2(
                intValue = 0,
                stringValue = "",
                listStringValue = listOf(),
            ),
            stateUpdates[0]
        )
        assertEquals(
            StubState2(
                intValue = expectedIntValue,
                stringValue = "",
                listStringValue = listOf(),
            ),
            stateUpdates[1]
        )
        assertEquals(
            StubState2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = listOf(),
            ),
            stateUpdates[2]
        )
        assertEquals(
            StubState2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue
            ),
            stateUpdates[3]
        )
        assertEquals(
            StubState2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue + expectedStringValue
            ),
            stateUpdates[4]
        )
        job.cancel()
    }

    @Test
    fun check_suspend_action_and_subscription_state_by_selector_with_mapping() = runTest {
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val store = createStubStore()
        val stubSelector2 = Selector<StubState2, StubState2VO> { toVO() }
        val data = store.select(stubSelector2)

        val stateUpdates = mutableListOf<StubState2VO>()
        val job = launch { data.toList(stateUpdates) }
        store.dispatcher.dispatch(
            StubSuspendAction2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue,
            )
        )

        assertEquals(
            StubState2VO(
                intValue = 0,
                stringValue = "",
                listStringValue = listOf(),
            ),
            stateUpdates[0]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = "",
                listStringValue = listOf(),
            ),
            stateUpdates[1]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = listOf(),
            ),
            stateUpdates[2]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue
            ),
            stateUpdates[3]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue + expectedStringValue
            ),
            stateUpdates[4]
        )
        job.cancel()
    }

    @Test
    fun check_suspend_action_and_subscription_state_by_selector_with_mapping_as_function() = runTest {
        val expectedIntValue = 42
        val expectedStringValue = "42"
        val expectedListStringValue = listOf("42")

        val store = createStubStore()
        val data = store.select<StubState2, StubState2VO> { toVO() }

        val stateUpdates = mutableListOf<StubState2VO>()
        val job = launch { data.toList(stateUpdates) }
        store.dispatcher.dispatch(
            StubSuspendAction2(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue,
            )
        )

        assertEquals(
            StubState2VO(
                intValue = 0,
                stringValue = "",
                listStringValue = listOf(),
            ),
            stateUpdates[0]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = "",
                listStringValue = listOf(),
            ),
            stateUpdates[1]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = listOf(),
            ),
            stateUpdates[2]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue
            ),
            stateUpdates[3]
        )
        assertEquals(
            StubState2VO(
                intValue = expectedIntValue,
                stringValue = expectedStringValue,
                listStringValue = expectedListStringValue + expectedStringValue
            ),
            stateUpdates[4]
        )
        job.cancel()
    }
}