package su.tease.project.feature.shop.presentation.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.save.action.SaveShopActions
import su.tease.project.feature.shop.presentation.select.SelectShopPage
import su.tease.project.feature.shop.presentation.select.reducer.SelectShopState as S

class SelectShopPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is SelectShopPage.OnSelectAction -> copy(savedShop = null)
        is SaveShopActions.OnSaveSuccess -> copy(savedShop = action.shop)
        else -> this
    }
}

@Parcelize
data class SelectShopState(val savedShop: Shop? = null) : State
