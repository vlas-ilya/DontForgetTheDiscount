package su.tease.core.mvi.navigation

import android.os.Parcelable

sealed interface NavigationTarget : Parcelable {

    fun some(target: NavigationTarget) = this::class == target::class

    interface App : NavigationTarget
    interface Feature : NavigationTarget
    interface Page : NavigationTarget
}
