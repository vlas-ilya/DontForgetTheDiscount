package su.tease.project.feature.shop.presentation.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.shop.domain.entity.Shop

@Parcelize
sealed class ExternalSelectShopActions : PlainAction {
    data class OnSelected(val shop: Shop) : ExternalSelectShopActions()
    data object OnFinish : ExternalSelectShopActions()
}