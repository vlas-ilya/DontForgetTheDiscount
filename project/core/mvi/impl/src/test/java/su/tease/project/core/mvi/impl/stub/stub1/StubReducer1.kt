package su.tease.project.core.mvi.impl.stub.stub1

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer

class StubReducer1 : Reducer<StubState1> {
    override val initState: StubState1 = StubState1()

    override fun StubState1.onAction(action: PlainAction): StubState1 = when (action) {
        is StubAction1.OnUpdateIntValue -> onUpdateIntValue(action)
        is StubAction1.OnUpdateStringValue -> onUpdateStringValue(action)
        is StubAction1.OnUpdateListStringValue -> onUpdateListStringValue(action)
        is StubAction1.OnAddToListStringValue -> onAddToListStringValue(action)
        else -> this
    }

    private fun StubState1.onUpdateIntValue(action: StubAction1.OnUpdateIntValue) =
        copy(intValue = action.intValue)

    private fun StubState1.onUpdateStringValue(action: StubAction1.OnUpdateStringValue) =
        copy(stringValue = action.stringValue)

    private fun StubState1.onUpdateListStringValue(action: StubAction1.OnUpdateListStringValue) =
        copy(listStringValue = action.listStringValue)

    private fun StubState1.onAddToListStringValue(action: StubAction1.OnAddToListStringValue) =
        copy(listStringValue = listStringValue + action.stringValue)
}
