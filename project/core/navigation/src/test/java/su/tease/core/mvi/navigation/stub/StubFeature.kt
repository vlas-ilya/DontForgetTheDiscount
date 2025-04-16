package su.tease.core.mvi.navigation.stub

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget

@Parcelize
sealed interface StubFeature : NavigationTarget.Feature {
    data object Feature1 : StubFeature

    data object Feature2 : StubFeature

    data object Feature3 : StubFeature

    data object Feature4 : StubFeature

    data class Feature5(val url: String) : StubFeature

    data class Feature6(val url: String) : StubFeature

    data class Feature7(val url: String) : StubFeature

    data class Feature8(val url: String) : StubFeature
}
