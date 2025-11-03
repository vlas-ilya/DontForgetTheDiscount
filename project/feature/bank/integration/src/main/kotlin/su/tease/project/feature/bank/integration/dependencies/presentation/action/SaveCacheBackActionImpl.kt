package su.tease.project.feature.bank.integration.dependencies.presentation.action

import org.koin.core.context.loadKoinModules
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.bank.integration.mapper.cashback.toExternal
import su.tease.project.feature.bank.integration.module.integration.bankCashBackIntegrationModule
import su.tease.project.feature.bank.presentation.action.SaveBankAccountActionRequest
import su.tease.project.feature.bank.presentation.action.SaveCacheBackAction
import su.tease.project.feature.cashback.presentation.SaveCashBackFeature as ExternalSaveCashBackFeature

class SaveCacheBackActionImpl : SaveCacheBackAction {
    override fun run(payload: SaveBankAccountActionRequest) = suspendAction {
        loadKoinModules(bankCashBackIntegrationModule)
        dispatch(
            NavigationAction.ForwardToFeature(
                feature = ExternalSaveCashBackFeature(
                    id = payload.id,
                    preset = payload.cashBackPreset?.toExternal(),
                    owner = payload.bankAccount?.toExternal(),
                    size = payload.size,
                    date = payload.date?.toExternal(),
                ),
            )
        )
    }
}
