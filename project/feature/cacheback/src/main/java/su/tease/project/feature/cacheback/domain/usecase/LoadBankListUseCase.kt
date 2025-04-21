package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.cacheback.domain.entity.Bank

interface LoadBankListUseCase : MviNoParamUseCase

@Parcelize
sealed class LoadBankListAction : PlainAction {

    data object OnLoad : LoadBankListAction()
    data class OnSuccess(val list: PersistentList<Bank>) : LoadBankListAction()
    data object OnFail : LoadBankListAction()
}
