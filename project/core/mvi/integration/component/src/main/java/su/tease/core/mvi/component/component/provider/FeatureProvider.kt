package su.tease.core.mvi.component.component.provider

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget

data class FeatureProvider<T : NavigationTarget.Feature>(
    val target: Class<T>,
    val component: NavigationScope<T>.() -> BaseFeatureComponent<T>,
)

inline fun <reified T : NavigationTarget.Feature> Module.feature(
    noinline component: NavigationScope<T>.() -> BaseFeatureComponent<T>
) {
    single(named(T::class.java.name)) {
        FeatureProvider(T::class.java, component)
    } bind FeatureProvider::class
}
