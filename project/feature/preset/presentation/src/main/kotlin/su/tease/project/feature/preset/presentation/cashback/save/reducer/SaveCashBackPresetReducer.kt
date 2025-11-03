package su.tease.project.feature.preset.presentation.cashback.save.reducer

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetError
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetActions as CashBack
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectBankPresetActionActions as Bank
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectIconActions as Icon
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectMccCodeActions as MccCode
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectShopPresetActionActions as Shop
import su.tease.project.feature.preset.presentation.cashback.save.reducer.SaveCashBackPresetState as S

class SaveCashBackPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is CashBack -> onSave(action)
        is Icon -> onIcon(action)
        is MccCode -> onMccCode(action)
        is Bank -> onBank(action)
        is Shop -> onShop(action)
        else -> this
    }

    private fun S.onSave(action: CashBack) = when (action) {
        is CashBack.OnInit -> S(action)
        is CashBack.OnSave -> copy(status = Loading, error = null)
        is CashBack.OnSaveSuccess -> S(status = Success)
        is CashBack.OnSaveFail -> copy(status = Failed, error = action.error)
    }

    private fun S.onIcon(action: Icon) = when (action) {
        is Icon.OnSelected -> copy(iconPreset = action.iconPreset)
    }

    private fun S.onBank(action: Bank) = when (action) {
        is Bank.OnSelected -> copy(cashBackOwnerPreset = action.bankPreset)
    }

    private fun S.onShop(action: Shop) = when (action) {
        is Shop.OnSelected -> copy(cashBackOwnerPreset = action.shopPreset)
    }

    private fun S.onMccCode(action: MccCode) = when (action) {
        is MccCode.OnSelected -> copy(mccCodes = action.mccCodes)
    }
}

@Parcelize
data class SaveCashBackPresetState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val name: String? = null,
    val info: String? = null,
    val iconPreset: CashBackIconPreset? = null,
    val cashBackOwnerPreset: CashBackOwnerPreset? = null,
    val mccCodes: PersistentList<MccCodePreset>? = null,
    val error: SaveCashBackPresetError? = null,
    val wasValidation: Boolean = false,
) : State {
    constructor(action: CashBack.OnInit) : this(
        id = action.preset?.id,
        name = action.preset?.name,
        info = action.preset?.info,
        iconPreset = action.preset?.iconPreset,
        cashBackOwnerPreset = action.ownerPreset ?: action.preset?.cashBackOwnerPreset,
        mccCodes = action.preset?.mccCodes,
    )
}
