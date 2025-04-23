package su.tease.project.feature.cacheback.presentation.add.page

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor

class CodesSelectPage(
    store: Store<*>,
    private val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
) : BasePageComponent(store) {

    @Composable
    override fun Compose() {
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: PersistentList<CacheBackCode>?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: PersistentList<CacheBackCode>
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: PersistentList<CacheBackCode>?,
        ) = Target(T::class.java.name, selected)
    }
}
