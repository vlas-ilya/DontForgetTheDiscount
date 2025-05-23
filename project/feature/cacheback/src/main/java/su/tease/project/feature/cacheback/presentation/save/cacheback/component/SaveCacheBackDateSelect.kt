package su.tease.project.feature.cacheback.presentation.save.cacheback.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.design.component.controls.dropdown.DFDropdownMenu
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toMonthYear

@Composable
fun SaveCacheBackDateSelect(
    dateState: State<CacheBackDate>,
    dates: PersistentList<CacheBackDate>,
    onSelect: (CacheBackDate) -> Unit,
    dateProvider: DateProvider,
    modifier: Modifier = Modifier,
) {
    DFFormElement(
        label = stringResource(R.string.item_cache_back_date_title),
        modifier = modifier.fillMaxWidth()
    ) {
        DFDropdownMenu(
            selected = dateState.value,
            items = dates,
            onItemClick = { onSelect(it) },
            text = { dateProvider.toText(it.toMonthYear()) },
            modifier = Modifier.padding(bottom = Theme.sizes.padding4),
            placeholder = R.string.item_cache_back_date_placeholder
        )
    }
}
