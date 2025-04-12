package su.tease.project.core.mvi.impl.store.stub.stub1

import su.tease.project.core.mvi.api.action.Action

sealed interface StubAction1: Action {
    data class OnUpdateIntValue(val intValue: Int): StubAction1
    data class OnUpdateStringValue(val stringValue: String): StubAction1
    data class OnUpdateListStringValue(val listStringValue: List<String>): StubAction1
    data class OnAddToListStringValue(val stringValue: String): StubAction1
}
