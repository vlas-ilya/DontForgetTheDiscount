package su.tease.core.mvi.navigation

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PageNavigation(
    val name: NavigationTarget.Page
) : Navigation {

    @IgnoredOnParcel
    override val top
        get() = this
}
