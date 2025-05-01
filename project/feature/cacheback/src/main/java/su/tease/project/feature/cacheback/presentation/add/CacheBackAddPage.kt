package su.tease.project.feature.cacheback.presentation.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.isTrue
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackUseCase
import su.tease.project.feature.cacheback.presentation.AddFormState
import su.tease.project.feature.cacheback.presentation.CacheBackReducer
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.add.component.BankSelect
import su.tease.project.feature.cacheback.presentation.add.component.CodesSelect
import su.tease.project.feature.cacheback.presentation.add.component.IconSelect
import su.tease.project.feature.cacheback.presentation.add.component.InfoEditText
import su.tease.project.feature.cacheback.presentation.add.component.NameEditText
import su.tease.project.feature.cacheback.presentation.add.component.SaveButton
import su.tease.project.feature.cacheback.presentation.add.component.SizeSelect
import su.tease.project.feature.cacheback.presentation.add.page.CacheBackAddPageFailed
import su.tease.project.feature.cacheback.presentation.add.page.CacheBackAddPageLoading
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction as Add

class CacheBackAddPage(
    store: Store<*>,
    private val addCacheBackUseCase: AddCacheBackUseCase,
) : BasePageComponent(store) {

    private val name = mutableStateOf(CacheBackName(""))
    private val info = mutableStateOf(CacheBackInfo(""))
    private val size = mutableStateOf(CacheBackSize(DEFAULT_PERCENT))

    init {
        dispatch(Add.OnInit)
    }

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) {
            appConfig {
                copy(titleRes = R.string.page_add_cache_back_title)
            }
        }

        val status = selectAsState(status).value

        LaunchedEffect(status) { if (status == LoadingStatus.Success) back() }

        when (status) {
            null,
            LoadingStatus.Success -> return

            LoadingStatus.Init -> CacheBackAddPageForm()
            LoadingStatus.Loading -> CacheBackAddPageLoading()
            LoadingStatus.Failed -> CacheBackAddPageFailed()
        }
    }

    @Composable
    private fun CacheBackAddPageForm() {
        val bank = selectAsState(bank)
        val icon = selectAsState(icon)
        val codes = selectAsState(codes)
        val addForm = selectAsState(addForm)
        val errors = addForm.map { it?.errors }

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
        ) {
            BankSelect(
                bankState = bank,
                onSelect = { forward(BankSelectPage<CacheBackReducer>(bank.value)) },
                error = runIf(errors.value?.bank.isTrue()) { stringResource(R.string.item_select_cache_back_bank_error) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            NameEditText(
                nameState = name,
                onChange = { name.value = it },
                error = runIf(errors.value?.name.isTrue()) { stringResource(R.string.item_cache_back_name_error) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            InfoEditText(
                infoState = info,
                onChange = { info.value = it },
                error = runIf(errors.value?.info.isTrue()) { stringResource(R.string.item_cache_back_info_error) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                IconSelect(
                    iconState = icon,
                    onSelect = { forward(IconSelectPage<CacheBackReducer>(icon.value)) },
                    error = errors.value?.icon.isTrue(),
                    modifier = Modifier.wrapContentWidth(),
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding12))
                SizeSelect(
                    sizeState = size,
                    onChange = { size.value = it },
                    error = runIf(errors.value?.icon.isTrue()) { stringResource(R.string.item_cache_back_size_error) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            CodesSelect(
                codesState = codes,
                onSelect = { forward(CodesSelectPage<CacheBackReducer>(codes.value)) },
                error = runIf(errors.value?.icon.isTrue()) { stringResource(R.string.item_select_cache_back_codes_error) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1F))
            SaveButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { addForm.value?.let { dispatch(addCacheBackUseCase(it)) } }
            )
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}

private val status = Selector<CacheBackState, LoadingStatus?> { addForm.status }
private val addForm = Selector<CacheBackState, AddFormState?> { addForm }
private val bank = Selector<CacheBackState, BankPreset?> { addForm.bank }
private val icon = Selector<CacheBackState, IconPreset?> { addForm.icon }
private val codes = Selector<CacheBackState, PersistentList<CacheBackCode>?> { addForm.codes }
private const val DEFAULT_PERCENT = 5
