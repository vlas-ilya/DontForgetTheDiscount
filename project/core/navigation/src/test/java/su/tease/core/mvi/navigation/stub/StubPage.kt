package su.tease.core.mvi.navigation.stub

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget

@Parcelize
sealed interface StubPage : NavigationTarget.Page {
    data object Page1 : StubPage

    data object Page2 : StubPage

    data object Page3 : StubPage

    data object Page4 : StubPage

    data class Page5(val url: String) : StubPage

    data class Page6(val url: String) : StubPage

    data class Page7(val url: String) : StubPage

    data class Page8(val url: String) : StubPage
}
