package su.tease.project.feature.shop.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.shop.domain.entity.Shop

interface SaveShopAction : MviUseCase<SaveShopActionRequest>

@Parcelize
sealed class SaveShopActions : PlainAction {
    data class OnInit(val shop: Shop? = null) : SaveShopActions()
    data object OnSave : SaveShopActions()
    data class OnSaveSuccess(val target: String, val shop: Shop) : SaveShopActions()
    data object OnSaveFail : SaveShopActions()
}

data class SaveShopActionRequest(
    val target: String,
    val shop: Shop
)
