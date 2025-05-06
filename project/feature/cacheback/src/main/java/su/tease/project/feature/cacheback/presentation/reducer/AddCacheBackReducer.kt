package su.tease.project.feature.cacheback.presentation.reducer

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
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
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction as Add

@Parcelize
data class AddCacheBackState(
    val status: LoadingStatus = Init,
    val date: CacheBackDate = defaultCacheBackDate,
    val bank: BankPreset? = null,
    val name: CacheBackName? = null,
    val info: CacheBackInfo? = null,
    val icon: IconPreset? = null,
    val size: CacheBackSize? = null,
    val codes: PersistentList<CacheBackCode> = persistentListOf(),
    val wasValidation: Boolean = false,
) : State

class AddCacheBackReducer : Reducer<AddCacheBackState> {

    override val initState = AddCacheBackState()

    override fun AddCacheBackState.onAction(action: PlainAction) = when (action) {
        is Add -> onAdd(action)
        is BankSelectPage.OnSelectAction -> onBankSelect(action)
        is IconSelectPage.OnSelectAction -> onIconSelect(action)
        is CodesSelectPage.OnSelectAction -> onCodesSelect(action)
        else -> this
    }

    private fun AddCacheBackState.onAdd(action: Add) = when (action) {
        is Add.OnInit -> action.addFormState
        is Add.OnSave -> copy(status = Loading)
        is Add.OnSaveSuccess -> AddCacheBackState(status = Success)
        is Add.OnSaveFail -> copy(status = Failed)
        is AddCacheBackAction.OnSetDate -> copy(date = action.date)
    }

    private fun AddCacheBackState.onBankSelect(action: BankSelectPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(bank = action.selected) }

    private fun AddCacheBackState.onIconSelect(action: IconSelectPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(icon = action.selected) }

    private fun AddCacheBackState.onCodesSelect(action: CodesSelectPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(codes = action.selected) }

    companion object {
        private fun String.current() = this == AddCacheBackReducer::class.java.name
    }
}
