package su.tease.project.feature.shop.presentation.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.utils.ext.transformIf
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.save.action.SaveShopActions.OnSaveSuccess as Save
import su.tease.project.feature.shop.presentation.select.SelectShopPage.OnInit as Init
import su.tease.project.feature.shop.presentation.select.SelectShopPage.OnSelectAction as Select
import su.tease.project.feature.shop.presentation.select.reducer.SelectShopState as S

class SelectShopPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is Init -> S()
        is Select -> copy(savedShop = null)
        is Save -> transformIf(action.target.current()) { copy(savedShop = action.shop) }
        else -> this
    }

    private fun String.current() = this == SelectShopPageReducer::class.java.name
}

@Parcelize
data class SelectShopState(val savedShop: Shop? = null) : State
