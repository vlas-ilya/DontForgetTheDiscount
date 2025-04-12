package su.tease.project.core.mvi.impl.store.stub

import su.tease.project.core.mvi.impl.middleware.SuspendMiddleware
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.store.StoreImpl
import su.tease.project.core.mvi.impl.store.utils.combine
import su.tease.project.core.mvi.impl.store.stub.stub1.StubReducer1
import su.tease.project.core.mvi.impl.store.stub.stub2.StubReducer2

fun createStubStore(): Store<*> {
    val middlewares = listOf(SuspendMiddleware())
    val reducer = combine(
        reducer1 = StubReducer1(),
        reducer2 = StubReducer2(),
    )
    return StoreImpl(
        reducer = reducer,
        middlewares = middlewares,
    )
}
