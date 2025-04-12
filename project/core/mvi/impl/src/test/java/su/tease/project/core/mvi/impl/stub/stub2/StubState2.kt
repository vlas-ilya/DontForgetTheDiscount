package su.tease.project.core.mvi.impl.stub.stub2

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.state.State

@Parcelize
data class StubState2(
    val intValue: Int = 0,
    val stringValue: String = "",
    val listStringValue: List<String> = listOf()
): State
