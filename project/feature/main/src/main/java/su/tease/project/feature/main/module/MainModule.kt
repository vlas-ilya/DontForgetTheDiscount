package su.tease.project.feature.main.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.app
import su.tease.project.feature.main.presentation.MainApp

val mainModule = module {
    app<MainApp.Target> { MainApp(it.store) }
}
