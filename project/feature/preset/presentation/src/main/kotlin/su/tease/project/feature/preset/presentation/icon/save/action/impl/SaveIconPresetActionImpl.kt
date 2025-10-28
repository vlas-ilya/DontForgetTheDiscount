package su.tease.project.feature.preset.presentation.icon.save.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.icon.save.SaveIconPresetPage.SelectIconTarget
import su.tease.project.feature.preset.presentation.icon.save.action.IconOwner
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetAction
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetActions

class SaveIconPresetActionImpl(
    private val presetInteractor: PresetInteractor,
    private val uuidProvider: UuidProvider,
) : SaveIconPresetAction {

    override fun run(request: IconOwner) = interceptSuspendAction {
        dispatch(SaveIconPresetActions.OnInit).join()
        dispatch(NavigationAction.ForwardToPage(SelectIconTarget(request))).join()
        interceptAction(
            SaveIconPresetActions.OnFinish::class,
            SaveIconPresetActions.OnIconSelected::class
        ).onSuccess {
            dispatch(SaveIconPresetActions.OnIconSelected(it.iconInfo)).join()
            dispatch(SaveIconPresetActions.OnSaveStart).join()
            try {
                val id = uuidProvider.uuid()
                it.save(id)
                dispatch(SaveIconPresetActions.OnSaveSuccess).join()
            } catch (_: Throwable) {
                dispatch(SaveIconPresetActions.OnSaveFailed).join()
            }
        }
    }

    private suspend fun SaveIconPresetActions.OnIconSelected.save(id: String) =
        when (iconInfo.iconOwner) {
            IconOwner.ForBank -> presetInteractor.save(BankIconPreset(id, iconInfo.path))
            IconOwner.ForCashback -> presetInteractor.save(CashBackIconPreset(id, iconInfo.path))
            IconOwner.ForShop -> presetInteractor.save(ShopIconPreset(id, iconInfo.path))
        }
}
