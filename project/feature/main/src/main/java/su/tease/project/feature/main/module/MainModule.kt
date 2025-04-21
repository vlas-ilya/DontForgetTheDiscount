package su.tease.project.feature.main.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.app
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.main.presentation.MainApp1
import su.tease.project.feature.main.presentation.MainApp2
import su.tease.project.feature.main.presentation.MainFeature1
import su.tease.project.feature.main.presentation.MainFeature2
import su.tease.project.feature.main.presentation.MainFeature3
import su.tease.project.feature.main.presentation.MainPage1
import su.tease.project.feature.main.presentation.MainPage2
import su.tease.project.feature.main.presentation.MainPage3
import su.tease.project.feature.main.presentation.MainPage4

val mainModule = module {

    page<MainPage1.Target> { MainPage1(it.store, it.target) }
    page<MainPage2.Target> { MainPage2(it.store) }
    page<MainPage3.Target> { MainPage3(it.store) }
    page<MainPage4.Target> { MainPage4(it.store) }

    feature<MainFeature1.Target> { MainFeature1(it.store) }
    feature<MainFeature2.Target> { MainFeature2(it.store) }
    feature<MainFeature3.Target> { MainFeature3(it.store) }

    app<MainApp1.Target> { MainApp1(it.store) }
    app<MainApp2.Target> { MainApp2(it.store) }
}
