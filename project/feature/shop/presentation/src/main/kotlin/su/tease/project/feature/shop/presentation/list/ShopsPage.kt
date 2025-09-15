@file:Suppress("EmptyFunctionBlock", "UnusedPrivateProperty")

package su.tease.project.feature.shop.presentation.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.feature.shop.presentation.list.action.LoadShopsActions
import su.tease.project.feature.shop.presentation.list.utils.toCashBackDate

class ShopsPage(
    store: Store<*>,
    private val resourceProvider: ResourceProvider,
    private val dateProvider: DateProvider,
) : BasePageComponent<ShopsPage.Target>(store) {

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    init {
        dispatch(LoadShopsActions.OnDateSelect(dateProvider.current().toCashBackDate()))
    }

    @Composable
    override fun invoke() {
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
