package su.tease.project.core.mvi.impl.store.stub.stub2

import su.tease.project.core.mvi.api.action.Action

sealed interface StubAction2: Action {
    data class OnUpdateIntValue(val intValue: Int): StubAction2
    data class OnUpdateStringValue(val stringValue: String): StubAction2
    data class OnUpdateListStringValue(val listStringValue: List<String>): StubAction2
    data class OnAddToListStringValue(val stringValue: String): StubAction2
}
