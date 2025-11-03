package su.tease.project.feature.preset.presentation.cashback.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset

interface SaveCashBackSelectIconAction : MviUseCase<CashBackIconPreset?>

@Parcelize
sealed class SaveCashBackSelectIconActions : PlainAction {
    data class OnSelected(val iconPreset: CashBackIconPreset) : SaveCashBackSelectIconActions()
}

