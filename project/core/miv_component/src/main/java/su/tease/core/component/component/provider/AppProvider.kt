package su.tease.core.component.component.provider

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.mvi.navigation.NavigationTarget

data class AppProvider<T : NavigationTarget.App>(
    val target: Class<T>,
    val component: Scope.(T) -> BaseAppComponent
)

inline fun <reified T : NavigationTarget.App> Module.app(
    noinline component: Scope.(T) -> BaseAppComponent
) {
    single(named(T::class.java.name)) {
        AppProvider(T::class.java, component)
    } bind AppProvider::class
}
