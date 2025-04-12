package su.tease.project.core.mvi.impl.stub.stub1

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction

@Parcelize
sealed interface StubAction1 : PlainAction {
    data class OnUpdateIntValue(val intValue: Int) : StubAction1
    data class OnUpdateStringValue(val stringValue: String) : StubAction1
    data class OnUpdateListStringValue(val listStringValue: List<String>) : StubAction1
    data class OnAddToListStringValue(val stringValue: String) : StubAction1
}
