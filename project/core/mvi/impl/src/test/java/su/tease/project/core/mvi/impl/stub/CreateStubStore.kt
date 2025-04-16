package su.tease.project.core.mvi.impl.stub

import kotlinx.coroutines.test.TestScope
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.combine.CombinedState
import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.impl.middleware.SuspendMiddleware
import su.tease.project.core.mvi.impl.store.StoreImpl
import su.tease.project.core.mvi.impl.stub.stub1.StubReducer1
import su.tease.project.core.mvi.impl.stub.stub1.StubState1
import su.tease.project.core.mvi.impl.stub.stub2.StubReducer2
import su.tease.project.core.mvi.impl.stub.stub2.StubState2

internal fun createStubStore(): Store<CombinedState<StubState1, StubState2>> {
    val middlewares = listOf(SuspendMiddleware())
    val reducer =
        combine(
            reducer1 = StubReducer1(),
            reducer2 = StubReducer2(),
        )
    return StoreImpl(
        reducer = reducer,
        middlewares = middlewares,
        coroutineScope = TestScope(),
    )
}
