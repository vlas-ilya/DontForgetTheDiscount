package su.tease.project.core.utils.ext

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // Для Android 11+
        WindowCompat.setDecorFitsSystemWindows(this, false)
        val controller = WindowCompat.getInsetsController(this, this.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        // Для старых версий
        decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
    }
}

fun Window.showSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(this, true)
        val controller = WindowCompat.getInsetsController(this, this.decorView)
        controller.show(WindowInsetsCompat.Type.systemBars())
    } else {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}

fun Window.isNavigationBarVisible(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insets = decorView.rootWindowInsets
        val bottom = insets
            ?.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars())
            ?.bottom
            ?: 0
        bottom > 0
    } else {
        val resources = context.resources
        val id = resources.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )
        id > 0 && resources.getDimensionPixelSize(id) > 0
    }
}
