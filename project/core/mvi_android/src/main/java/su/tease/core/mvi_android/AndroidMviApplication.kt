package su.tease.core.mvi_android

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import su.tease.project.core.mvi.api.middleware.Middleware
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.store.StoreImpl

abstract class AndroidMviApplication(
    reducer: Reducer<*>,
    middlewares: List<Middleware>,
    coroutineScope: CoroutineScope = MainScope()
) : Application() {

    val store: Store<*> = StoreImpl(
        reducer = reducer,
        middlewares = middlewares,
        coroutineScope = coroutineScope,
    )
}
