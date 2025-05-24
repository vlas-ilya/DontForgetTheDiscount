package su.tease.project.feature.cashback.presentation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cashback.presentation.list.ListCashBackPage

class CashBackFeature(
    store: Store<*>,
) : BaseFeatureComponent<CashBackFeature.Target>(store) {

    companion object {
        operator fun invoke() = feature(
            Target,
            ListCashBackPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
