package su.tease.core.mvi.component.component.provider

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.core.mvi.navigation.NavigationTarget

data class AppProvider<T : NavigationTarget.App>(
    val target: Class<T>,
    val component: NavigationScope<T>.() -> BaseAppComponent<T>,
)

inline fun <reified T : NavigationTarget.App> Module.app(
    noinline component: NavigationScope<T>.() -> BaseAppComponent<T>
) {
    single(named(T::class.java.name)) {
        AppProvider(T::class.java, component)
    } bind AppProvider::class
}
