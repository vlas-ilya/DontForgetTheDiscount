package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.middleware.intercept.InterceptSuspendMiddleware
import su.tease.project.core.mvi.middleware.logger.LoggerMiddleware
import su.tease.project.core.mvi.middleware.suspend.SuspendMiddleware
import timber.log.Timber

val dontForgetTheDiscountMiddlewares = listOf(
    LoggerMiddleware { Timber.d("Redux action: $it") },
    SuspendMiddleware(),
    InterceptSuspendMiddleware(),
)
