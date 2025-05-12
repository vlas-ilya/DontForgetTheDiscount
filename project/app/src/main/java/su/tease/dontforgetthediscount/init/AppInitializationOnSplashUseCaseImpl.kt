package su.tease.dontforgetthediscount.init

import su.tease.feature.splash.AppInitializationOnSplashUseCase
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.domain.repository.SyncPresetRepository
import timber.log.Timber

class AppInitializationOnSplashUseCaseImpl(
    private val syncPresetRepository: SyncPresetRepository
) : AppInitializationOnSplashUseCase {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun onInit() = withDefault {
        try {
            syncPresetRepository.sync()
        } catch (t: Throwable) {
            Timber.e(t)
        }
    }
}
