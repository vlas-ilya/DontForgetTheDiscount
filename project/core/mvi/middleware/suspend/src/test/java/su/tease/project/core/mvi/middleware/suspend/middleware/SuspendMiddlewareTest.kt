@file:Suppress("TooManyFunctions", "LongMethod", "LargeClass")

package su.tease.project.core.mvi.middleware.suspend.middleware

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.middleware.suspend.stub.createStubStore
import su.tease.project.core.mvi.middleware.suspend.stub.stub1.StubState1
import su.tease.project.core.mvi.middleware.suspend.stub.stub2.StubState2
import su.tease.project.core.mvi.middleware.suspend.stub.stub2.StubSuspendAction2

class SuspendMiddlewareTest {

    @Test
    fun `Check suspend action and subscription state by selector with mapping`() =
        runTest {
            val expectedIntValue = 42
            val expectedStringValue = "42"
            val expectedListStringValue = listOf("42")

            val store = createStubStore()
            val data = store.state

            val stateUpdates = mutableListOf<State>()
            val job = launch { data.toList(stateUpdates) }
            store.dispatcher.dispatch(
                StubSuspendAction2(
                    intValue = expectedIntValue,
                    stringValue = expectedStringValue,
                    listStringValue = expectedListStringValue,
                ),
            ).join()

            assertEquals(
                combine(
                    StubState1(),
                    StubState2(),
                ),
                stateUpdates[0],
            )
            assertEquals(
                combine(
                    StubState1(),
                    StubState2().copy(
                        intValue = expectedIntValue,
                    ),
                ),
                stateUpdates[1],
            )
            assertEquals(
                combine(
                    StubState1(),
                    StubState2().copy(
                        intValue = expectedIntValue,
                        stringValue = expectedStringValue,
                    ),
                ),
                stateUpdates[2],
            )
            assertEquals(
                combine(
                    StubState1(),
                    StubState2().copy(
                        intValue = expectedIntValue,
                        stringValue = expectedStringValue,
                        listStringValue = expectedListStringValue,
                    ),
                ),
                stateUpdates[3],
            )
            assertEquals(
                combine(
                    StubState1(),
                    StubState2().copy(
                        intValue = expectedIntValue,
                        stringValue = expectedStringValue,
                        listStringValue = expectedListStringValue + expectedStringValue,
                    ),
                ),
                stateUpdates[4],
            )
            assertEquals(
                combine(
                    StubState1(),
                    StubState2().copy(
                        intValue = expectedIntValue,
                        stringValue = expectedStringValue,
                        listStringValue = expectedListStringValue + expectedStringValue,
                    ),
                ),
                store.state.value,
            )

            job.cancel()
        }
}
