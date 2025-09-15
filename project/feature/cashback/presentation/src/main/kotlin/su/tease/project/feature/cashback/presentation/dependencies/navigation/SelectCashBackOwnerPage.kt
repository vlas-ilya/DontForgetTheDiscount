package su.tease.project.feature.cashback.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.cashback.domain.entity.CashBackOwner

object SelectCashBackOwnerPage {

    @Parcelize
    data class OnSelectAction(val selected: CashBackOwner?) : PlainAction
}
