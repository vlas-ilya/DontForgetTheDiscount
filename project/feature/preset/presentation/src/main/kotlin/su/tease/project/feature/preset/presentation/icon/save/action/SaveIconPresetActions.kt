package su.tease.project.feature.preset.presentation.icon.save.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.presentation.icon.save.reducer.IconInfo

interface SaveIconPresetAction : MviUseCase<IconOwner>

@Parcelize
sealed class SaveIconPresetActions : PlainAction {
    data object OnInit : SaveIconPresetActions()
    data class OnIconSelected(val iconInfo: IconInfo) : SaveIconPresetActions()
    data object OnSaveStart : SaveIconPresetActions()
    data object OnSaveSuccess : SaveIconPresetActions()
    data object OnSaveFailed : SaveIconPresetActions()
    data object OnFinish : SaveIconPresetActions()
}

@Parcelize
sealed class IconOwner : Parcelable {
    data object ForBank : IconOwner()
    data object ForShop : IconOwner()
    data object ForCashback : IconOwner()
}
