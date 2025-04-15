package su.tease.project.feature.main.module

import org.koin.dsl.module
import su.tease.core.component.component.provider.app
import su.tease.core.component.component.provider.feature
import su.tease.core.component.component.provider.page
import su.tease.project.core.mvi.api.store.Store
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
    page<MainPage1.Target> { MainPage1(store = get<Store<*>>(), target = it) }
    page<MainPage2.Target> { MainPage2(store = get<Store<*>>()) }
    page<MainPage3.Target> { MainPage3(store = get<Store<*>>()) }
    page<MainPage4.Target> { MainPage4(store = get<Store<*>>()) }

    feature<MainFeature1.Target> { MainFeature1(store = get<Store<*>>()) }
    feature<MainFeature2.Target> { MainFeature2(store = get<Store<*>>()) }
    feature<MainFeature3.Target> { MainFeature3(store = get<Store<*>>()) }

    app<MainApp1.Target> { MainApp1(store = get<Store<*>>()) }
    app<MainApp2.Target> { MainApp2(store = get<Store<*>>()) }
}
