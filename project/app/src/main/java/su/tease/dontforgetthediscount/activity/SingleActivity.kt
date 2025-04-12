package su.tease.dontforgetthediscount.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import su.tease.project.design.theme.impl.Theme
import su.tease.project.design.theme.impl.ThemeValue
import su.tease.project.design.theme.impl.switchTheme

class SingleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        switchTheme(ThemeValue.LIGHT)
        setContent {
            Theme {
                // TODO
            }
        }
    }
}
