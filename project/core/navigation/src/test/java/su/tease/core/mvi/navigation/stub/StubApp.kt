package su.tease.core.mvi.navigation.stub

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget

@Parcelize
sealed interface StubApp : NavigationTarget.App {
    data object App1 : StubApp

    data object App2 : StubApp

    data object App3 : StubApp

    data object App4 : StubApp

    data class App5(val url: String) : StubApp

    data class App6(val url: String) : StubApp

    data class App7(val url: String) : StubApp

    data class App8(val url: String) : StubApp
}
