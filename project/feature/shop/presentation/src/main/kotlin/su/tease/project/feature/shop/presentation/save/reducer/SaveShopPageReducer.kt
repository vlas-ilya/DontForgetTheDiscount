package su.tease.project.feature.shop.presentation.save.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.shop.domain.entity.ShopPreset
import su.tease.project.feature.shop.presentation.save.action.SaveShopActions
import su.tease.project.feature.shop.presentation.save.action.SaveShopSelectShopPresetActions as ShopPresetActions

class SaveShopPageReducer : Reducer<SaveShopState> {
    override val initState = SaveShopState()

    override fun SaveShopState.onAction(action: PlainAction): SaveShopState =
        when (action) {
            is SaveShopActions -> onSave(action)
            is ShopPresetActions -> onShopPreset(action)
            else -> this
        }

    private fun SaveShopState.onSave(action: SaveShopActions) = when (action) {
        is SaveShopActions.OnSave -> copy(status = Loading)
        is SaveShopActions.OnSaveSuccess -> SaveShopState(status = Success)
        is SaveShopActions.OnSaveFail -> SaveShopState(status = Failed)
        is SaveShopActions.OnInit -> SaveShopState(
            status = Init,
            id = action.shop?.id,
            ownerPreset = action.shop?.preset,
            customName = action.shop?.customName,
        )
    }

    private fun SaveShopState.onShopPreset(action: ShopPresetActions) = when (action) {
        is ShopPresetActions.OnSelected -> copy(
            ownerPreset = action.shopPreset,
            customName = customName ?: action.shopPreset.name
        )
    }
}

@Parcelize
data class SaveShopState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val ownerPreset: ShopPreset? = null,
    val customName: String? = null,
) : State
