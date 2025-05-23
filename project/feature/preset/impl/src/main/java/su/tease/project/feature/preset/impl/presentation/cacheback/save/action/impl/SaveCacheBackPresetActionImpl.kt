package su.tease.project.feature.preset.impl.presentation.cacheback.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetAction
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetActions
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetError

class SaveCacheBackPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInterceptor: PresetInterceptor,
) : SaveCacheBackPresetAction {

    override fun run(request: CacheBackPreset) = suspendAction {
        dispatch(SaveCacheBackPresetActions.OnSave)
        try {
            val cacheBackPresets = presetInterceptor.cacheBackPresets(request.bankPreset.id)
            if (cacheBackPresets.any { it.name == request.name }) {
                dispatch(SaveCacheBackPresetActions.OnSaveFail(SaveCacheBackPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            val cacheBackPreset = request.copy(
                id = request.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid()
            )
            presetInterceptor.save(cacheBackPreset)
            dispatch(SaveCacheBackPresetActions.OnSaveSuccess(cacheBackPreset))
        } catch (_: RepositoryException) {
            dispatch(SaveCacheBackPresetActions.OnSaveFail(SaveCacheBackPresetError.SOME_ERROR))
        }
    }
}
