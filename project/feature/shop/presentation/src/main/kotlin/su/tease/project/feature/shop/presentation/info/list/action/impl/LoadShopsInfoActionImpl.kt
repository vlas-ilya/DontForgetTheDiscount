package su.tease.project.feature.shop.presentation.info.list.action.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.info.list.action.LoadShopsInfoAction
import su.tease.project.feature.shop.presentation.info.list.action.LoadShopsInfoActions

class LoadShopsInfoActionImpl(
    private val interactor: ShopInterceptor,
) : LoadShopsInfoAction {

    override fun run() = suspendAction {
        dispatch(LoadShopsInfoActions.OnLoad)
        val list = interactor.listWithoutCashbacks()
        try {
            dispatch(LoadShopsInfoActions.OnSuccess(list.sortShops()))
        } catch (_: RepositoryException) {
            dispatch(LoadShopsInfoActions.OnFail)
        }
    }
}

private fun List<Shop>.sortShops(): PersistentList<Shop> = this
    .sortedBy { it.customName }
    .toPersistentList()
