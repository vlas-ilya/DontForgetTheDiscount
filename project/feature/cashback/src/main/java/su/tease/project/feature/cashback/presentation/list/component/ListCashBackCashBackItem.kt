package su.tease.project.feature.cashback.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.core.mvi.component.component.impl.NavigationComponent
import su.tease.core.mvi.component.component.impl.NavigationComponentImpl
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.ext.toPercent
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.FRACTIONAL_SIZE
import su.tease.project.feature.cashback.presentation.list.reducer.CashBackInfoDialogAction
import su.tease.project.feature.cashback.presentation.save.SaveCashBackFeature
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackRequest
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIcon
import su.tease.project.feature.preset.api.presentation.component.CashBackPresetIconSize
import su.tease.project.design.icons.R as RIcons

data class ListCashBackCashBackItem(
    private val bankAccount: BankAccount,
    private val cashBack: CashBack,
    private val store: Store<*>,
) : LazyListItem, NavigationComponent by NavigationComponentImpl(store) {

    override val key = CASH_BACK + cashBack.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    SaveCashBackFeature(
                        SaveCashBackRequest(
                            id = cashBack.id,
                            bankAccount = bankAccount,
                            cashBackPreset = cashBack.cashBackPreset,
                            size = cashBack.size,
                            date = cashBack.date,
                        )
                    ).forward()
                }
                .padding(horizontal = Theme.sizes.padding8)
                .padding(start = Theme.sizes.padding20),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CashBackPresetIcon(
                cashBackPreset = cashBack.cashBackPreset,
                size = CashBackPresetIconSize.SMALL,
            )
            Spacer(modifier = Modifier.width(Theme.sizes.padding4))
            Row(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DFText(
                    text = stringResource(
                        R.string.item_cash_back_in_list_label_percent,
                        cashBack.size.toPercent(FRACTIONAL_SIZE),
                    ),
                    style = Theme.fonts.monospace,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFText(
                    text = cashBack.cashBackPreset.name,
                    style = Theme.fonts.text,
                )
            }
            runIf(
                cashBack.cashBackPreset.info.isNotBlank() ||
                    cashBack.cashBackPreset.mccCodes.isNotEmpty()
            ) {
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFIconButton(
                    icon = RIcons.drawable.comment_info,
                    size = DFIconButtonSize.S,
                    onClick = {
                        store.dispatcher.dispatch(
                            CashBackInfoDialogAction.OnShow(bankAccount to cashBack)
                        )
                    },
                    tint = Theme.colors.iTint,
                )
            }
        }
    }
}

private const val CASH_BACK = "CASH_BACK"
