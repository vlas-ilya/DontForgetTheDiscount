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
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetActions
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetError
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetActions as Shop
import su.tease.project.feature.preset.presentation.shop.save.action.SelectShopIconActions as Icon
import su.tease.project.feature.preset.presentation.shop.save.reducer.SaveShopPresetState as S

class SaveShopPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Shop -> onSave(action)
        is Icon -> onIcon(action)
        else -> this
    }

    private fun S.onSave(action: Shop) = when (action) {
        is Shop.OnInit -> S(action.initShopPreset)
        is Shop.OnSave -> copy(status = Loading, error = null)
        is Shop.OnSaved -> copy(status = Success, error = null)
        is Shop.OnSaveFail -> copy(status = Failed, error = action.error)
    }

    private fun S.onIcon(action: Icon) = when (action) {
        is Icon.OnSelected -> copy(icon = action.iconPreset)
    }
}

@Parcelize
data class SaveShopPresetState(
    val status: LoadingStatus = Init,
    val icon: ShopIconPreset? = defaultShopIconPreset,
    val name: String = DEFAULT_NAME,
    val error: SaveShopPresetError? = null,
    val wasValidation: Boolean = false,
) : State {

    constructor(shopPreset: ShopPreset?) : this(
        icon = shopPreset?.iconPreset ?: defaultShopIconPreset,
        name = shopPreset?.name ?: DEFAULT_NAME,
    )
}

private val defaultShopIconPreset = ShopIconPreset(
    id = "1af9a2da-d564-44ba-bb34-dbd82c602c9a",
    iconUrl = "https://dontforgetthediscount.ru/static/img/shop/0.png"
)

private const val DEFAULT_NAME = ""
