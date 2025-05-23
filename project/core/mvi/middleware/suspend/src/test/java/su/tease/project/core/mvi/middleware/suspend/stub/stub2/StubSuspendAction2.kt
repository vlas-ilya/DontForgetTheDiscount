package su.tease.project.core.mvi.middleware.suspend.stub.stub2

import kotlinx.coroutines.delay
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.middleware.suspend.SuspendAction

class StubSuspendAction2(
    private val intValue: Int,
    private val stringValue: String,
    private val listStringValue: List<String>,
) : SuspendAction {

    override suspend fun invoke(dispatcher: Dispatcher) {
        delay(100)
        dispatcher.dispatch(StubAction2.OnUpdateIntValue(intValue))
        delay(100)
        dispatcher.dispatch(StubAction2.OnUpdateStringValue(stringValue))
        delay(100)
        dispatcher.dispatch(StubAction2.OnUpdateListStringValue(listStringValue))
        delay(100)
        dispatcher.dispatch(StubAction2.OnAddToListStringValue(stringValue))
        delay(100)
    }
}
