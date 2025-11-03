package su.tease.project.feature.preset.presentation.icon.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.IconPreset
import su.tease.project.feature.preset.presentation.icon.entity.IconType

interface SaveIconPresetAction : MviUseCase<IconType>

@Parcelize
sealed class ExternalSaveIconPresetActions : PlainAction {
    data class OnSaved(val iconPreset: IconPreset) : ExternalSaveIconPresetActions()
    data object OnFinish : ExternalSaveIconPresetActions()
}
