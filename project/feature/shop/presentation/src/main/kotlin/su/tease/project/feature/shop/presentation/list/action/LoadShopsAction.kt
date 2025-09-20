package su.tease.project.feature.shop.presentation.list.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop

interface LoadShopsAction : MviUseCase<CashBackDate?>

@Parcelize
sealed class LoadShopsActions : PlainAction {

    data object OnLoad : LoadShopsActions()
    data class OnDateSelect(val date: CashBackDate) : LoadShopsActions()
    data object OnFail : LoadShopsActions()
    data class OnSuccess(
        val currentDate: CashBackDate,
        val dates: PersistentList<CashBackDate>,
        val list: PersistentList<Shop>,
    ) : LoadShopsActions()
}
