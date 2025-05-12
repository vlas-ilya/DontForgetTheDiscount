package su.tease.core.mvi.component.component.provider

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

class NavigationScope<T : NavigationTarget>(
    val scope: Scope,
    val store: Store<*>,
    val target: NavigationTarget,
) {
    inline fun <reified T : Any> get(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        if (store is T) return store
        if (target is T) return target
        return scope.get(T::class, qualifier, parameters)
    }
}
