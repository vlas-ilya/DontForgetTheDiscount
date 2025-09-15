package su.tease.project.feature.bank.presentation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.bank.presentation.list.BankAccountsPage

class BankAccountFeature(
    store: Store<*>,
) : BaseFeatureComponent<BankAccountFeature.Target>(store) {

    companion object {
        operator fun invoke() = feature(
            Target,
            BankAccountsPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
