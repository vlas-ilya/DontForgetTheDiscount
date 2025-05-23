package su.tease.project.feature.cacheback.presentation.save.cacheback.reducer

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
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.cacheback.presentation.save.bank.select.SelectBankAccountPage.OnSelectAction as SelectBankAccount
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackActions as Save
import su.tease.project.feature.cacheback.presentation.save.cacheback.reducer.SaveCacheBackState as S
import su.tease.project.feature.preset.api.presentation.cacheback.select.SelectCacheBackPresetPage.OnSelectAction as SelectCacheBackPreset

class SaveCacheBackReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Save -> onSave(action)
        is SelectBankAccount -> onSelectBankAccount(action)
        is SelectCacheBackPreset -> onSelectCacheBackPreset(action)
        else -> this
    }

    private fun S.onSave(action: Save) = when (action) {
        is Save.OnSave -> copy(status = Loading)
        is Save.OnSaveSuccess -> S(status = Success)
        is Save.OnSaveFail -> copy(status = Failed)
        is Save.OnSetDate -> copy(date = action.date)
        is Save.OnInit -> {
            S(
                status = Init,
                id = action.request?.id,
                bankAccount = action.request?.bankAccount,
                cacheBackPreset = action.request?.cacheBackPreset,
                size = action.request?.size,
                date = action.request?.date,
            )
        }
    }

    private fun S.onSelectBankAccount(action: SelectBankAccount): S {
        return transformIf(action.target.current()) {
            copy(
                bankAccount = action.selected,
                cacheBackPreset = cacheBackPreset.takeIf { bankAccount?.id == action.selected?.id }
            )
        }
    }

    private fun S.onSelectCacheBackPreset(action: SelectCacheBackPreset) =
        transformIf(action.target.current()) { copy(cacheBackPreset = action.selected) }

    private fun String.current() = this == this@SaveCacheBackReducer::class.java.name
}

@Parcelize
data class SaveCacheBackState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val bankAccount: BankAccount? = null,
    val cacheBackPreset: CacheBackPreset? = null,
    val size: Int? = null,
    val date: CacheBackDate? = null,
    val wasValidation: Boolean = false,
) : State
