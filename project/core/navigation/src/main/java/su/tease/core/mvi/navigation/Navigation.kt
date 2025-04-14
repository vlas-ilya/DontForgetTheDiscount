package su.tease.core.mvi.navigation

import android.os.Parcelable

interface Navigation : Parcelable {
    val top: PageNavigation
}
