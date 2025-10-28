package su.tease.project.feature.info.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.info.presentation.InfoFeature
import su.tease.project.feature.info.presentation.list.InfoListPage

val infoIntegrationModule = module {
    feature { InfoFeature(get()) }
    page { InfoListPage(get(), get()) }
}
