package su.tease.core.mvi.component.component.provider

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget

data class PageProvider<T : NavigationTarget.Page>(
    val target: Class<T>,
    val component: NavigationScope<T>.() -> BasePageComponent<T>,
)

inline fun <reified T : NavigationTarget.Page> Module.page(
    noinline component: NavigationScope<T>.() -> BasePageComponent<T>
) {
    single(named(T::class.java.name)) {
        PageProvider(T::class.java, component)
    } bind PageProvider::class
}
