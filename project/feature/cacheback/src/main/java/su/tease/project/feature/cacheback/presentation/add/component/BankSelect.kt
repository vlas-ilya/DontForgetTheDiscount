package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.text.DFPlaceholder
import su.tease.project.design.component.controls.text.DFSmallTitle
import su.tease.project.design.theme.impl.utils.Preview
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

@Composable
fun BankSelect(
    bankState: State<BankPreset?>,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bank by bankState

    Column(
        modifier = modifier
            .clickable { onSelect() }
            .padding(vertical = Theme.sizes.padding2)
    ) {
        DFSmallTitle(
            text = stringResource(R.string.bank),
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding4)
                .padding(bottom = Theme.sizes.padding2)
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bank?.let {
                BankPresetPreview(it)
            } ?: run {
                DFPlaceholder(
                    text = stringResource(R.string.choose_bank),
                    modifier = Modifier
                        .padding(horizontal = Theme.sizes.padding4)
                )
            }
        }
    }
}

@Composable
@Preview
private fun BankSelectPreview() = Preview {
    val emptyBank = remember { mutableStateOf<BankPreset?>(null) }
    BankSelect(
        bankState = emptyBank,
        onSelect = {},
        modifier = Modifier
    )

    val someBank = remember {
        mutableStateOf<BankPreset?>(
            BankPreset(
                id = "ccba0311-bd4d-4aa5-bcdb-3b506a4e6658",
                name = "Ozon Банк",
                icon = IconPreset("https://dontforgetthediscount.ru/static/img/bank/ozon-bank.png")
            )
        )
    }

    BankSelect(
        bankState = someBank,
        onSelect = {},
        modifier = Modifier
    )
}
