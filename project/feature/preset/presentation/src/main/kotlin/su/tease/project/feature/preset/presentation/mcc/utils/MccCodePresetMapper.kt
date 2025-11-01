package su.tease.project.feature.preset.presentation.mcc.utils

import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.presentation.mcc.component.MccCodePresetPreview

fun List<MccCodePreset>.toUi(
    store: Store<*>,
    onClick: ((MccCodePreset) -> Unit)?
): LazyListItems = mapPersistent {
    MccCodePresetPreview(
        mccCodePreset = it,
        store = store,
        onClick = onClick,
    )
}
