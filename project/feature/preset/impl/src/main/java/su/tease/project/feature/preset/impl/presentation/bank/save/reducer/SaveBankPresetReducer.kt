package su.tease.project.feature.preset.impl.presentation.bank.save.reducer

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
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.impl.presentation.bank.save.action.SaveBankPresetError
import su.tease.project.feature.preset.impl.presentation.icon.select.IconSelectPresetPage
import su.tease.project.feature.preset.impl.presentation.bank.save.action.SaveBankPresetActions as Save
import su.tease.project.feature.preset.impl.presentation.bank.save.reducer.SaveBankPresetState as S

class SaveBankPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Save -> onSave(action)
        is IconSelectPresetPage.OnSelectAction -> onIconSelect(action)
        else -> this
    }

    private fun S.onSave(action: Save) = when (action) {
        is Save.OnInit -> S()
        is Save.OnSave -> copy(status = Loading, error = null)
        is Save.OnSaveFail -> copy(status = Failed, error = action.error)
        is Save.OnSaveSuccess -> S(status = Success)
    }

    private fun S.onIconSelect(action: IconSelectPresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(icon = action.selected as? BankIconPreset) }

    private fun String.current() = this == this@SaveBankPresetReducer::class.java.name
}

@Parcelize
data class SaveBankPresetState(
    val status: LoadingStatus = Init,
    val icon: BankIconPreset? = defaultBankIconPreset,
    val name: String = "",
    val error: SaveBankPresetError? = null,
    val wasValidation: Boolean = false,
) : State

private val defaultBankIconPreset = BankIconPreset(
    id = "1af9a2da-d564-44ba-bb34-dbd82c602c9a",
    iconUrl = "https://dontforgetthediscount.ru/static/img/bank/bank.png"
)
