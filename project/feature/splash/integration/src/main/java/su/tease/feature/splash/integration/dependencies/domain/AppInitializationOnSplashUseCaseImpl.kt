package su.tease.feature.splash.integration.dependencies.domain

import ru.tease.project.feature.splash.domain.AppInitializationOnSplashUseCase
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.domain.interactor.SyncPresetInteractor
import timber.log.Timber

class AppInitializationOnSplashUseCaseImpl(
    private val syncPresetInteractor: SyncPresetInteractor
) : AppInitializationOnSplashUseCase {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun onInit() = withDefault {
        try {
            syncPresetInteractor.sync()
        } catch (t: Throwable) {
            Timber.Forest.e(t)
        }
    }
}