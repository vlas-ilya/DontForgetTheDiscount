package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.middleware.intercept.InterceptSuspendMiddleware
import su.tease.project.core.mvi.middleware.suspend.SuspendMiddleware

val dontForgetTheDiscountMiddlewares = listOf(
    SuspendMiddleware(),
    InterceptSuspendMiddleware(),
)
