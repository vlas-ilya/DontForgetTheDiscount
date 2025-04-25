@file:Suppress("DEPRECATION")

package su.tease.core.mvi.navigation

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import su.tease.project.core.utils.uuid.ImplicitUuid

@Parcelize
data class PageNavigation(
    val name: NavigationTarget.Page,
    override val id: String = ImplicitUuid.make(),
) : Navigation {

    @IgnoredOnParcel
    override val page
        get() = this

    fun some(feature: PageNavigation) = name.some(feature.name)
}
