package su.tease.project.feature.info.presentation.navigation

import androidx.compose.runtime.Immutable
import su.tease.core.mvi.navigation.NavigationTarget

@Immutable
interface NavigationTargets {
    val banks: NavigationTarget.Page
    val bankPresets: NavigationTarget.Page
    val bankIconPresets: NavigationTarget.Page
    val shops: NavigationTarget.Page
    val shopPresets: NavigationTarget.Page
    val shopIconPresets: NavigationTarget.Page
    val cashBackPresets: NavigationTarget.Page
    val cashBackIconPresets: NavigationTarget.Page
    val mccCodePresets: NavigationTarget.Page
}