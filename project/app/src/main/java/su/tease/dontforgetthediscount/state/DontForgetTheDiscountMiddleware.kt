package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.impl.middleware.SuspendMiddleware

val dontForgetTheDiscountMiddlewares = listOf(
    SuspendMiddleware(),
)
