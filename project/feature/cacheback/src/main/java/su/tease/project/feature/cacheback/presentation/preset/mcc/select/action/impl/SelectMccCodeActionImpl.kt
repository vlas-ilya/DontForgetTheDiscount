package su.tease.project.feature.cacheback.presentation.preset.mcc.select.action.impl

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset
import su.tease.project.feature.cacheback.domain.interceptor.PresetInterceptor
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.action.OnSelectMccCodeInit
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.action.SelectMccCodeAction
import su.tease.project.feature.cacheback.presentation.preset.mcc.select.action.SelectMccCodeRequest

class SelectMccCodeActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInterceptor: PresetInterceptor,
) : SelectMccCodeAction {

    override fun run(request: SelectMccCodeRequest) = suspendAction {
        dispatch(OnSelectMccCodeInit)
        val codes = savePresets(request.codes)
        dispatch(SelectMccCodePresetPage.OnSelectAction(request.target, codes))
    }

    private suspend fun savePresets(codes: List<String>): PersistentList<MccCodePreset> {
        val persisted = presetInterceptor.mccCodePresets().associateBy { it.code }
        return codes.mapPersistent {
            MccCodePreset(
                id = persisted[it]?.id ?: uuidProvider.uuid(),
                code = persisted[it]?.code ?: it,
                name = persisted[it]?.name ?: "",
                info = persisted[it]?.info ?: "",
            )
        }
            .onEach { presetInterceptor.save(it) }
    }
}
