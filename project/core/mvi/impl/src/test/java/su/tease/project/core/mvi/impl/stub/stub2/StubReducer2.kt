package su.tease.project.core.mvi.impl.stub.stub2

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer

class StubReducer2 : Reducer<StubState2> {
    override val initState: StubState2 = StubState2()

    override fun StubState2.onAction(action: PlainAction): StubState2 =
        when (action) {
            is StubAction2.OnUpdateIntValue -> onUpdateIntValue(action)
            is StubAction2.OnUpdateStringValue -> onUpdateStringValue(action)
            is StubAction2.OnUpdateListStringValue -> onUpdateListStringValue(action)
            is StubAction2.OnAddToListStringValue -> onAddToListStringValue(action)
            else -> this
        }

    private fun StubState2.onUpdateIntValue(action: StubAction2.OnUpdateIntValue) = copy(intValue = action.intValue)

    private fun StubState2.onUpdateStringValue(action: StubAction2.OnUpdateStringValue) = copy(
        stringValue = action.stringValue
    )

    private fun StubState2.onUpdateListStringValue(action: StubAction2.OnUpdateListStringValue) =
        copy(listStringValue = action.listStringValue)

    private fun StubState2.onAddToListStringValue(action: StubAction2.OnAddToListStringValue) =
        copy(listStringValue = listStringValue + action.stringValue)
}
