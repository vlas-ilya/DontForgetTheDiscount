package su.tease.project.feature.icon.integration

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.data.repository.SaveIconRepositoryImpl
import su.tease.project.feature.domain.repository.SaveIconRepository
import su.tease.project.feature.domain.usecase.SaveIconUseCase
import su.tease.project.feature.icon.presentation.SelectIconPage
import su.tease.project.feature.icon.presentation.action.SelectIconAction
import su.tease.project.feature.icon.presentation.action.impl.SelectIconActionImpl

val iconIntegrationModule = module {
    factory<SaveIconRepository> { SaveIconRepositoryImpl(get()) }
    factory { SaveIconUseCase(get(), get()) }
    factory<SelectIconAction> { SelectIconActionImpl(get()) }
    page { SelectIconPage(get(), get(), get()) }
}
