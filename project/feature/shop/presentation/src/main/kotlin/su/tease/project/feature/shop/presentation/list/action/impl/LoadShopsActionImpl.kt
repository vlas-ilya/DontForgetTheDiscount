package su.tease.project.feature.shop.presentation.list.action.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.CashBackPreset
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.list.action.LoadShopsAction
import su.tease.project.feature.shop.presentation.list.action.LoadShopsActions
import su.tease.project.feature.shop.presentation.list.utils.toCashBackDate

class LoadShopsActionImpl(
    private val interactor: ShopInterceptor,
    private val dateProvider: DateProvider,
) : LoadShopsAction {

    override fun run(request: CashBackDate?) = suspendAction {
        dispatch(LoadShopsActions.OnLoad)
        try {
            val date = request ?: dateProvider.current().toCashBackDate()
            val list = interactor.filterBy(date)
            val dates = interactor.listDates().takeIf { it.isNotEmpty() } ?: persistentListOf(date)
            dispatch(LoadShopsActions.OnSuccess(date, dates, list.sortBanks()))
        } catch (_: RepositoryException) {
            dispatch(LoadShopsActions.OnFail)
        }
    }
}

private fun List<Shop>.sortBanks(): PersistentList<Shop> = this
    .map { it.copy(cashBacks = it.cashBacks.sortCashBacks()) }
    .sortedBy { it.preset.name }
    .toPersistentList()

private fun List<CashBack>.sortCashBacks(): PersistentList<CashBack> = this
    .map { it.copy(preset = it.preset.withCodeSorted()) }
    .sortedBy { it.preset.name }
    .toPersistentList()

private fun CashBackPreset.withCodeSorted() = this
    .copy(mccCodes = mccCodes.sortedBy { it.code }.toPersistentList())
