package su.tease.project.feature.cacheback.presentation.reducer

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
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddBankAction
import su.tease.project.feature.cacheback.domain.usecase.AddBankError
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddBankAction as Add

@Parcelize
data class AddBankState(
    val status: LoadingStatus = Init,
    val icon: IconPreset? = null,
    val name: String = "",
    val error: AddBankError? = null,
    val wasValidation: Boolean = false,
) : State

class AddBankReducer : Reducer<AddBankState> {

    override val initState = AddBankState()

    override fun AddBankState.onAction(action: PlainAction) = when (action) {
        is Add -> onAdd(action)
        is IconSelectPage.OnSelectAction -> onIconSelect(action)
        else -> this
    }

    private fun AddBankState.onAdd(action: Add): AddBankState = when (action) {
        is AddBankAction.OnInit -> AddBankState()
        is AddBankAction.OnSave -> copy(status = Loading, error = null)
        is AddBankAction.OnSaveFail -> copy(status = Failed, error = action.error)
        is AddBankAction.OnSaveSuccess -> AddBankState(status = Success)
    }

    private fun AddBankState.onIconSelect(action: IconSelectPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(icon = action.selected) }

    companion object {
        private fun String.current() = this == AddBankReducer::class.java.name
    }
}
