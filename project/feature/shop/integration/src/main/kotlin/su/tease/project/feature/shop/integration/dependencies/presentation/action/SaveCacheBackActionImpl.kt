package su.tease.project.feature.shop.integration.dependencies.presentation.action

import org.koin.core.context.loadKoinModules
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.shop.integration.mapper.cashback.toExternal
import su.tease.project.feature.shop.integration.module.integration.shopCashBackIntegrationModule
import su.tease.project.feature.shop.presentation.action.SaveBankAccountActionRequest
import su.tease.project.feature.shop.presentation.action.SaveCacheBackAction
import su.tease.project.feature.cashback.presentation.SaveCashBackFeature as ExternalSaveCashBackFeature

class SaveCacheBackActionImpl : SaveCacheBackAction {
    override fun run(payload: SaveBankAccountActionRequest) = suspendAction {
        loadKoinModules(shopCashBackIntegrationModule)
        dispatch(
            NavigationAction.ForwardToFeature(
                feature = ExternalSaveCashBackFeature(
                    id = payload.id,
                    preset = payload.cashBackPreset?.toExternal(),
                    owner = payload.shop?.toExternal(),
                    size = payload.size,
                    date = payload.date?.toExternal(),
                ),
            )
        )
    }
}
