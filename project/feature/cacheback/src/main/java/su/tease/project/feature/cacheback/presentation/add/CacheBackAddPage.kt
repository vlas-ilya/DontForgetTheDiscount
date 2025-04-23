package su.tease.project.feature.cacheback.presentation.add

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.page.DFPage
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
import su.tease.project.feature.cacheback.presentation.add.page.BankSelectPage
import su.tease.project.feature.cacheback.presentation.add.page.CacheBackAddPageFailed
import su.tease.project.feature.cacheback.presentation.add.page.CacheBackAddPageLoading
import su.tease.project.feature.cacheback.presentation.add.page.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.add.page.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction as Add

class CacheBackAddPage(
    store: Store<*>,
    private val addCacheBackUseCase: AddCacheBackUseCase,
) : BasePageComponent(store) {

    override fun RootContainerConfiguration.configure() {
        isFullscreen = true
    }

    override fun AppContainerConfiguration.configure() {
        hasNavigationBar = false
    }

    @Composable
    override fun Compose() {
        val status = selectAsState(status).value

        LaunchedEffect(status) { if (status == LoadingStatus.Success) back() }

        DFPage(
            title = stringResource(R.string.add_cache_back_page_title),
            onBackPressed = ::back,
        ) {
            when (status) {
                null -> return@DFPage
                LoadingStatus.Success -> return@DFPage
                LoadingStatus.Init -> CacheBackAddPageForm()
                LoadingStatus.Loading -> CacheBackAddPageLoading()
                LoadingStatus.Failed -> CacheBackAddPageFailed()
            }
        }
    }

    @Composable
    private fun CacheBackAddPageForm() {
        val bank = selectAsState(bank)
        val name = selectAsState(name)
        val info = selectAsState(info)
        val icon = selectAsState(icon)
        val size = selectAsState(size)
        val codes = selectAsState(codes)
        val addForm = selectAsState(addForm)

        Row {
            BankSelect(
                bankState = bank,
                onSelect = { forward(BankSelectPage<CacheBackReducer>(bank.value)) },
            )
            NameEditText(
                name = name,
                onChange = { dispatch(Add.OnNameChange(it)) },
            )
            InfoEditText(
                info = info,
                onChange = { dispatch(Add.OnInfoChange(it)) },
            )
            IconSelect(
                icon = icon,
                onSelect = { forward(IconSelectPage<CacheBackReducer>(icon.value)) },
            )
            SizeSelect(
                size = size,
                onChange = { dispatch(Add.OnSizeChange(it)) }
            )
            CodesSelect(
                codes = codes,
                onSelect = { forward(CodesSelectPage<CacheBackReducer>(codes.value)) }
            )
            SaveButton {
                addForm.value?.let { dispatch(addCacheBackUseCase(it)) }
            }
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}

private val status = Selector<CacheBackState, LoadingStatus?> { addForm.status }
private val addForm = Selector<CacheBackState, AddFormState?> { addForm }
private val bank = Selector<CacheBackState, BankPreset?> { addForm.bank }
private val name = Selector<CacheBackState, CacheBackName?> { addForm.name }
private val info = Selector<CacheBackState, CacheBackInfo?> { addForm.info }
private val icon = Selector<CacheBackState, IconPreset?> { addForm.icon }
private val size = Selector<CacheBackState, CacheBackSize?> { addForm.size }
private val codes = Selector<CacheBackState, PersistentList<CacheBackCode>?> { addForm.codes }
