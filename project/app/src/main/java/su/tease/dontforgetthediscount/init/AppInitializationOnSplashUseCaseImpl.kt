package su.tease.dontforgetthediscount.init

import su.tease.feature.splash.AppInitializationOnSplashUseCase
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.api.domain.interceptor.SyncPresetInterceptor
import timber.log.Timber

class AppInitializationOnSplashUseCaseImpl(
    private val syncPresetInterceptor: SyncPresetInterceptor
) : AppInitializationOnSplashUseCase {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun onInit() = withDefault {
        try {
            syncPresetInterceptor.sync()
        } catch (t: Throwable) {
            Timber.e(t)
        }
    }
}
