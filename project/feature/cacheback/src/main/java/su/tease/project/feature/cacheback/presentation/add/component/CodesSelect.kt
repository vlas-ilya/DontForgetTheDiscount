package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.utils.Callback
import su.tease.project.design.component.controls.text.DFSmallTitle
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode

@Composable
fun CodesSelect(
    codesState: State<PersistentList<CacheBackCode>?>,
    modifier: Modifier = Modifier,
    onSelect: Callback,
) {
    val selectedCodesText = codesState
        .map { list -> (list?.map { it.code.value } ?: emptyList()).joinToString() }

    Column(
        modifier = modifier
            .clickable { onSelect() }
            .padding(vertical = Theme.sizes.padding2)
    ) {
        DFSmallTitle(
            text = stringResource(R.string.codes),
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding4)
                .padding(bottom = Theme.sizes.padding2)
        )
        DFText(
            text = selectedCodesText.value,
        )
    }
}
