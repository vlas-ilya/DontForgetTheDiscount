package su.tease.project.feature.preset.presentation.shop.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetAction
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetActions
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetError

class SaveShopPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInteractor: PresetInteractor,
) : SaveShopPresetAction {

    override fun run(request: ShopPreset) = suspendAction {
        dispatch(SaveShopPresetActions.OnSave)
        val shopPreset = request.copy(
            id = request.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid(),
            name = request.name.trim()
        )
        try {
            val shops = presetInteractor.shopPresets()
            if (shops.any { it.name == shopPreset.name && it.id != request.id }) {
                dispatch(SaveShopPresetActions.OnSaveFail(SaveShopPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            presetInteractor.save(shopPreset)
            dispatch(SaveShopPresetActions.OnSaveSuccess(shopPreset))
        } catch (_: RepositoryException) {
            dispatch(SaveShopPresetActions.OnSaveFail(SaveShopPresetError.SOME_ERROR))
        }
    }
}
