package su.tease.project.feature.cashback.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.cashback.domain.entity.CashBackOwner

interface SaveCashBackSelectCashBackOwnerAction : MviNoParamUseCase

@Parcelize
sealed class SaveCashBackSelectCashBackOwnerActionActions : PlainAction {
    data class OnSelected(val cashBackOwner: CashBackOwner) :
        SaveCashBackSelectCashBackOwnerActionActions()
}
