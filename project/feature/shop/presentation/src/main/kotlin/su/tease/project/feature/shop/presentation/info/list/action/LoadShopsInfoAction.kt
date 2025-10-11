package su.tease.project.feature.shop.presentation.info.list.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.shop.domain.entity.Shop

interface LoadShopsInfoAction : MviNoParamUseCase

@Parcelize
sealed class LoadShopsInfoActions : PlainAction {
    data object OnLoad : LoadShopsInfoActions()
    data object OnFail : LoadShopsInfoActions()
    data class OnSuccess(val list: PersistentList<Shop>) : LoadShopsInfoActions()
}
