package su.tease.project.feature.preset.presentation.bank.save.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetError
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetActions as Bank
import su.tease.project.feature.preset.presentation.bank.save.action.SelectBankIconActions as Icon
import su.tease.project.feature.preset.presentation.bank.save.reducer.SaveBankPresetState as S

class SaveBankPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Bank -> onSave(action)
        is Icon -> onIcon(action)
        else -> this
    }

    private fun S.onSave(action: Bank) = when (action) {
        is Bank.OnInit -> S(action.initBankPreset)
        is Bank.OnSave -> copy(status = Loading, error = null)
        is Bank.OnSaved -> copy(status = Success, error = null)
        is Bank.OnSaveFail -> copy(status = Failed, error = action.error)
    }

    private fun S.onIcon(action: Icon) = when (action) {
        is Icon.OnSelected -> copy(icon = action.iconPreset)
    }
}

@Parcelize
data class SaveBankPresetState(
    val status: LoadingStatus = Init,
    val icon: BankIconPreset? = defaultBankIconPreset,
    val name: String = DEFAULT_NAME,
    val error: SaveBankPresetError? = null,
    val wasValidation: Boolean = false,
) : State {

    constructor(bankPreset: BankPreset?) : this(
        icon = bankPreset?.iconPreset ?: defaultBankIconPreset,
        name = bankPreset?.name ?: DEFAULT_NAME,
    )
}

private val defaultBankIconPreset = BankIconPreset(
    id = "1af9a2da-d564-44ba-bb34-dbd82c602c9a",
    iconUrl = "https://dontforgetthediscount.ru/static/img/bank/bank.png"
)

private const val DEFAULT_NAME = ""
