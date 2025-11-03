package su.tease.project.feature.preset.presentation.icon.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.IconPreset
import su.tease.project.feature.preset.presentation.icon.entity.IconType

interface SelectIconPresetAction : MviUseCase<IconType>

@Parcelize
sealed class ExternalSelectIconPresetAction : PlainAction {
    data class OnSelected(val iconPreset: IconPreset) : ExternalSelectIconPresetAction()
    data object OnFinish : ExternalSelectIconPresetAction()
}
