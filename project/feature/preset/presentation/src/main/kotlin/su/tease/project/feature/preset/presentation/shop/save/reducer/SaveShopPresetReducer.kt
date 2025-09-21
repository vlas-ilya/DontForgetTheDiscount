package su.tease.project.feature.preset.presentation.shop.save.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.utils.ext.transformIf
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetError
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetActions as Save
import su.tease.project.feature.preset.presentation.shop.save.reducer.SaveShopPresetState as S

class SaveShopPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Save -> onSave(action)
        is SelectIconPresetPage.OnSelectAction -> onIconSelect(action)
        else -> this
    }

    private fun S.onSave(action: Save) = when (action) {
        is Save.OnInit -> S()
        is Save.OnSave -> copy(status = Loading, error = null)
        is Save.OnSaveFail -> copy(status = Failed, error = action.error)
        is Save.OnSaveSuccess -> S(status = Success)
    }

    private fun S.onIconSelect(action: SelectIconPresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(icon = action.selected as? ShopIconPreset) }

    private fun String.current() = this == this@SaveShopPresetReducer::class.java.name
}

@Parcelize
data class SaveShopPresetState(
    val status: LoadingStatus = Init,
    val icon: ShopIconPreset? = defaultShopIconPreset,
    val name: String = "",
    val error: SaveShopPresetError? = null,
    val wasValidation: Boolean = false,
) : State

private val defaultShopIconPreset = ShopIconPreset(
    id = "1af9a2da-d564-44ba-bb34-dbd82c602c9a",
    iconUrl = "https://dontforgetthediscount.ru/static/img/shop/0.png"
)
