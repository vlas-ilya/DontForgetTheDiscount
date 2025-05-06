package su.tease.project.feature.cacheback.presentation.save

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.RedirectState
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackAction
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackUseCase
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackReducer
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackState
import su.tease.project.feature.cacheback.presentation.save.component.BankSelect
import su.tease.project.feature.cacheback.presentation.save.component.CodesSelect
import su.tease.project.feature.cacheback.presentation.save.component.DateSelect
import su.tease.project.feature.cacheback.presentation.save.component.IconSelect
import su.tease.project.feature.cacheback.presentation.save.component.InfoEditText
import su.tease.project.feature.cacheback.presentation.save.component.NameEditText
import su.tease.project.feature.cacheback.presentation.save.component.SaveAndAddMoreButton
import su.tease.project.feature.cacheback.presentation.save.component.SaveButton
import su.tease.project.feature.cacheback.presentation.save.component.SizeSelect
import su.tease.project.feature.cacheback.presentation.save.page.CacheBackAddPageFailed
import su.tease.project.feature.cacheback.presentation.save.page.CacheBackAddPageLoading
import su.tease.project.feature.cacheback.presentation.save.utls.SaveCacheBackForm
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackAction as Add

class SaveCacheBackPage(
    store: Store<*>,
    private val target: Target,
    private val dateProvider: DateProvider,
    private val saveCacheBackUseCase: SaveCacheBackUseCase,
) : BasePageComponent(store) {

    private val dateValue = target.saveCacheBackState.date
        .takeIf { it !== defaultCacheBackDate }
        ?: dateProvider.current().toCacheBackDate()

    private val dates = dateProvider.currentAndNext().mapPersistent { it.toCacheBackDate() }

    private val form = SaveCacheBackForm(
        bankValue = target.saveCacheBackState.bank,
        nameValue = target.saveCacheBackState.name ?: CacheBackName(""),
        infoValue = target.saveCacheBackState.info ?: CacheBackInfo(""),
        iconValue = target.saveCacheBackState.icon,
        sizeValue = target.saveCacheBackState.size ?: CacheBackSize(0),
        codesValue = target.saveCacheBackState.codes,
        dateValue = dateValue,
    )

    init {
        dispatch(Add.OnInit(target.saveCacheBackState))
    }

    @Composable
    override operator fun invoke() {
        AppConfig {
            copy(
                titleRes = (target.saveCacheBackState.id == null).choose(
                    R.string.page_save_cache_back_title_add,
                    R.string.page_save_cache_back_title_edit,
                )
            )
        }

        val status = selectAsState(SaveCacheBackState::status).value

        LaunchedEffect(status) {
            if (status != LoadingStatus.Success) return@LaunchedEffect

            if (form.addMore.value.not()) {
                dispatch(Add.OnInit(target.saveCacheBackState))
                back()
            } else {
                val bank = form.bank.value
                val date = form.date.value
                form.clean()
                dispatch(Add.OnInit(SaveCacheBackState(bank = bank, date = date)))
            }
        }

        when (status) {
            LoadingStatus.Success -> return
            LoadingStatus.Init -> CacheBackAddPageForm()
            LoadingStatus.Loading -> CacheBackAddPageLoading()
            LoadingStatus.Failed -> CacheBackAddPageFailed()
        }
    }

    @Composable
    private fun CacheBackAddPageForm() {
        RedirectState(selectAsState(SaveCacheBackState::date), form.date)
        RedirectState(selectAsState(SaveCacheBackState::bank), form.bank)
        RedirectState(selectAsState(SaveCacheBackState::icon), form.icon)
        RedirectState(selectAsState(SaveCacheBackState::codes), form.codes)
        val id = selectAsState(SaveCacheBackState::id)

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(WindowInsets.ime.asPaddingValues())
        ) {
            DateSelect(
                dateState = form.date,
                dates = dates,
                onSelect = { dispatch(SaveCacheBackAction.OnSetDate(it)) },
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
                onSelect = { forward(CodesSelectPage<SaveCacheBackReducer>(form.codes.value)) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1F))
            SaveButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { save(id.value) }
            )
            runIf(id.value == null) {
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                SaveAndAddMoreButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { saveAndAddMore() }
                )
            }
        }
    }

    private fun selectBank(@StringRes title: Int) {
        forward(
            BankSelectPage<SaveCacheBackReducer>(
                pageTitle = title,
                selected = form.bank.value
            )
        )
    }

    private fun selectIcon(@StringRes title: Int) {
        forward(
            IconSelectPage<SaveCacheBackReducer>(
                iconType = IconSelectPage.IconType.CACHE_BACK_ICON,
                pageTitle = title,
                selected = form.icon.value
            )
        )
    }

    private fun save(id: CacheBackId?) {
        form.makeResult(id)?.let {
            form.addMore.value = false
            dispatch(saveCacheBackUseCase(it))
        }
    }

    private fun saveAndAddMore() {
        form.makeResult()?.let {
            form.addMore.value = true
            dispatch(saveCacheBackUseCase(it))
        }
    }

    @Parcelize
    data class Target(
        val saveCacheBackState: SaveCacheBackState = SaveCacheBackState()
    ) : NavigationTarget.Page
}
