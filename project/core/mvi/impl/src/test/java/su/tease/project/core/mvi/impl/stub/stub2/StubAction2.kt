package su.tease.project.core.mvi.impl.stub.stub2

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction

@Parcelize
sealed interface StubAction2 : PlainAction {
    data class OnUpdateIntValue(val intValue: Int) : StubAction2

    data class OnUpdateStringValue(val stringValue: String) : StubAction2

    data class OnUpdateListStringValue(val listStringValue: List<String>) : StubAction2

    data class OnAddToListStringValue(val stringValue: String) : StubAction2
}
