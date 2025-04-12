package su.tease.project.core.mvi.impl.stub.stub1

import kotlinx.coroutines.delay
import su.tease.project.core.mvi.api.action.SuspendAction
import su.tease.project.core.mvi.api.store.Dispatcher

class StubSuspendAction1(
    private val intValue: Int,
    private val stringValue: String,
    private val listStringValue: List<String>,
): SuspendAction {
    override suspend fun invoke(dispatcher: Dispatcher) {
        delay(100)
        dispatcher.dispatch(StubAction1.OnUpdateIntValue(intValue))
        delay(100)
        dispatcher.dispatch(StubAction1.OnUpdateStringValue(stringValue))
        delay(100)
        dispatcher.dispatch(StubAction1.OnUpdateListStringValue(listStringValue))
        delay(100)
        dispatcher.dispatch(StubAction1.OnAddToListStringValue(stringValue))
        delay(100)
    }
}
