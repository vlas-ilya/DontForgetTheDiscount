package su.tease.project.feature.cacheback.presentation.add

import androidx.annotation.StringRes
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.RedirectState
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackUseCase
import su.tease.project.feature.cacheback.presentation.AddFormState
import su.tease.project.feature.cacheback.presentation.CacheBackReducer
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.add.component.BankSelect
import su.tease.project.feature.cacheback.presentation.add.component.CodesSelect
import su.tease.project.feature.cacheback.presentation.add.component.DateSelect
import su.tease.project.feature.cacheback.presentation.add.component.IconSelect
import su.tease.project.feature.cacheback.presentation.add.component.InfoEditText
import su.tease.project.feature.cacheback.presentation.add.component.NameEditText
import su.tease.project.feature.cacheback.presentation.add.component.SaveAndAddMoreButton
import su.tease.project.feature.cacheback.presentation.add.component.SaveButton
import su.tease.project.feature.cacheback.presentation.add.component.SizeSelect
import su.tease.project.feature.cacheback.presentation.add.page.CacheBackAddPageFailed
import su.tease.project.feature.cacheback.presentation.add.page.CacheBackAddPageLoading
import su.tease.project.feature.cacheback.presentation.add.utls.AddCacheBackForm
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction as Add

class AddCacheBackPage(
    store: Store<*>,
    target: Target,
    private val dateProvider: DateProvider,
    private val addCacheBackUseCase: AddCacheBackUseCase,
) : BasePageComponent(store) {

    private val dateValue = target.addFormState.date
        .takeIf { it !== defaultCacheBackDate }
        ?: dateProvider.current().toCacheBackDate()

    private val dates = dateProvider.currentAndNext().mapPersistent { it.toCacheBackDate() }

    private val form = AddCacheBackForm(dateValue)

    init { dispatch(Add.OnInit(target.addFormState)) }

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) { appConfig { copy(titleRes = R.string.page_add_cache_back_title) } }

        val status = selectAsState(status).value

        LaunchedEffect(status) {
            if (status != LoadingStatus.Success) return@LaunchedEffect

            if (form.addMore.value.not()) {
                back()
            } else {
                val bank = form.bank.value
                val date = form.date.value
                form.clean()
                dispatch(Add.OnInit(AddFormState(bank = bank, date = date)))
            }
        }

        when (status) {
            null -> return
            LoadingStatus.Success -> return
            LoadingStatus.Init -> CacheBackAddPageForm()
            LoadingStatus.Loading -> CacheBackAddPageLoading()
            LoadingStatus.Failed -> CacheBackAddPageFailed()
        }
    }

    @Composable
    private fun CacheBackAddPageForm() {
        RedirectState(selectAsState(date), form.date)
        RedirectState(selectAsState(bank), form.bank)
        RedirectState(selectAsState(icon), form.icon)
        RedirectState(selectAsState(codes), form.codes)

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
        ) {
            DateSelect(
                dateState = form.date,
                dates = dates,
                onSelect = { form.date.value = it },
                modifier = Modifier.fillMaxWidth(),
                dateProvider = dateProvider,
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            BankSelect(
                bankState = form.bank,
                onSelect = { selectBank(R.string.page_select_cache_back_bank_title) },
                error = form.ui { bankError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            NameEditText(
                nameState = form.name,
                onChange = { form.name.value = it },
                error = form.ui { nameError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            InfoEditText(
                infoState = form.info,
                onChange = { form.info.value = it },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                IconSelect(
                    iconState = form.icon,
                    onSelect = { selectIcon(R.string.page_select_cache_back_icon_title) },
                    error = form.ui { iconError },
                    modifier = Modifier.wrapContentWidth(),
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding8))
                SizeSelect(
                    sizeState = form.size,
                    onChange = { form.size.value = it },
                    error = form.ui { sizeError },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            CodesSelect(
                codesState = form.codes,
                onSelect = { forward(CodesSelectPage<CacheBackReducer>(form.codes.value)) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1F))
            SaveButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { save() }
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            SaveAndAddMoreButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { saveAndAddMore() }
            )
        }
    }

    private fun selectBank(@StringRes title: Int) {
        forward(
            BankSelectPage<CacheBackReducer>(
                pageTitle = title,
                selected = form.bank.value
            )
        )
    }

    private fun selectIcon(@StringRes title: Int) {
        forward(
            IconSelectPage<CacheBackReducer>(
                pageTitle = title,
                selected = form.icon.value
            )
        )
    }

    private fun save() {
        form.makeResult()?.let {
            form.addMore.value = false
            dispatch(addCacheBackUseCase(it))
        }
    }

    private fun saveAndAddMore() {
        form.makeResult()?.let {
            form.addMore.value = true
            dispatch(addCacheBackUseCase(it))
        }
    }

    @Parcelize
    data class Target(val addFormState: AddFormState = AddFormState()) : NavigationTarget.Page
}

private val status = Selector<CacheBackState, LoadingStatus?> { addForm.status }
private val bank = Selector<CacheBackState, BankPreset?> { addForm.bank }
private val date = Selector<CacheBackState, CacheBackDate> { addForm.date }
private val icon = Selector<CacheBackState, IconPreset?> { addForm.icon }
private val codes = Selector<CacheBackState, PersistentList<CacheBackCode>> { addForm.codes }
