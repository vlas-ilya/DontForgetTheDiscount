package su.tease.project.core.mvi.middleware.suspend.stub

import kotlinx.coroutines.test.TestScope
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.combine.combine
import su.tease.project.core.mvi.impl.store.StoreImpl
import su.tease.project.core.mvi.middleware.suspend.SuspendMiddleware
import su.tease.project.core.mvi.middleware.suspend.stub.stub1.StubReducer1
import su.tease.project.core.mvi.middleware.suspend.stub.stub2.StubReducer2

internal fun createStubStore(): Store<*> {
    val middlewares = listOf(SuspendMiddleware())
    val reducer = combine(StubReducer1(), StubReducer2())

    return StoreImpl(
        reducer = reducer,
        middlewares = middlewares,
        coroutineScope = TestScope(),
    )
}
