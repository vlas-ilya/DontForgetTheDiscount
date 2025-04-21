package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.impl.logger.StoreLogger
import timber.log.Timber

val dontForgetTheDiscountLogger = object : StoreLogger {
    override fun log(prevState: State, action: PlainAction, newState: State) {
        Timber.d("Redux action: $action")
    }
}
