package su.tease.project.feature.preset.impl.presentation.cashback.save.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetError

class SaveCashBackPresetForm(
    nameValue: String?,
    infoValue: String?,
    iconPresetValue: CashBackIconPreset?,
    bankPresetValue: BankPreset?,
    mccCodesValue: PersistentList<MccCodePreset>?,
    disabledBankPresetValue: BankPreset?,
) {
    init {
        if (bankPresetValue != null && disabledBankPresetValue != null) {
            require(bankPresetValue.id == disabledBankPresetValue.id)
        }
    }

    private val wasValidation = mutableStateOf(false)

    val name = mutableStateOf(nameValue ?: "")
    val info = mutableStateOf(infoValue ?: "")
    val iconPreset = mutableStateOf(iconPresetValue)
    val bankPreset = mutableStateOf(disabledBankPresetValue ?: bankPresetValue)
    val mccCodes = mutableStateOf(mccCodesValue ?: persistentListOf())

    val error = mutableStateOf<SaveCashBackPresetError?>(null)

    val nameError = check(name) {
        require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY)
        require(error.value == SaveCashBackPresetError.DUPLICATE_ERROR, FormFieldError.DUPLICATE)
    }
    val iconError = check(iconPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val bankPresetError = check(bankPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }

    val bankPresetEnabled = disabledBankPresetValue == null

    fun setName(value: String) {
        name.value = value
        error.value = null
    }

    fun makeResult(cashBackPresetId: String? = null): CashBackPreset? =
        listOf(nameError.value, iconError.value, bankPresetError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                CashBackPreset(
                    id = cashBackPresetId ?: "",
                    name = name.value,
                    info = info.value,
                    iconPreset = iconPreset.value!!,
                    bankPreset = bankPreset.value!!,
                    mccCodes = mccCodes.value,
                )
            }

    fun ui(block: SaveCashBackPresetForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        name.value = ""
        info.value = ""
        iconPreset.value = null
        bankPreset.value = null
        mccCodes.value = persistentListOf()
    }
}

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    DUPLICATE,
}
