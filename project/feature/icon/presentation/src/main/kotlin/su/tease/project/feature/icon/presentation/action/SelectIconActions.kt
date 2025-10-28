package su.tease.project.feature.icon.presentation.action

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.domain.usecase.FilePath

interface SelectIconAction : MviUseCase<SelectIconActionRequest>

@Parcelize
sealed class SelectIconActions : PlainAction {
    data object OnInit : SelectIconActions()
    data object OnStart : SelectIconActions()
    data object OnSave : SelectIconActions()
    data object OnSaveFail : SelectIconActions()
    data class OnSelect(val target: String, val filePath: FilePath) : SelectIconActions()
    data class OnFinish(val target: String) : SelectIconActions()
}

data class SelectIconActionRequest(
    val target: String,
    val uri: Uri,
    val scale: Float,
    val offset: Offset,
    val rotation: Float,
)
