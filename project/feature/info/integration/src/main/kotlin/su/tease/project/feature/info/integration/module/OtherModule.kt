package su.tease.project.feature.info.integration.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.info.presentation.OtherFeature
import su.tease.project.feature.info.presentation.list.InfoListPage

val otherModule = module {
    feature { OtherFeature(get()) }
    page { InfoListPage(get(), get()) }
}
