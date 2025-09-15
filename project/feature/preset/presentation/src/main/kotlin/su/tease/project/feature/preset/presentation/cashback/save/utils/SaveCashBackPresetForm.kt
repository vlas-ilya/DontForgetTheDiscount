package su.tease.project.feature.preset.presentation.cashback.save.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetError

class SaveCashBackPresetForm(
    nameValue: String?,
    infoValue: String?,
    iconPresetValue: CashBackIconPreset?,
    cashBackOwnerPreset: CashBackOwnerPreset?,
    mccCodesValue: PersistentList<MccCodePreset>?,
    disabledCashBackOwnerPresetValue: CashBackOwnerPreset?,
) {
    init {
        if (cashBackOwnerPreset != null && disabledCashBackOwnerPresetValue != null) {
            require(cashBackOwnerPreset.id == disabledCashBackOwnerPresetValue.id)
        }
    }

    private val wasValidation = mutableStateOf(false)

    val name = mutableStateOf(nameValue ?: "")
    val info = mutableStateOf(infoValue ?: "")
    val iconPreset = mutableStateOf(iconPresetValue)
    val cashBackOwnerPreset = mutableStateOf(disabledCashBackOwnerPresetValue ?: cashBackOwnerPreset)
    val mccCodes = mutableStateOf(mccCodesValue ?: persistentListOf())

    val error = mutableStateOf<SaveCashBackPresetError?>(null)

    val nameError = check(name) {
        require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY)
        require(error.value == SaveCashBackPresetError.DUPLICATE_ERROR, FormFieldError.DUPLICATE)
    }
    val iconError = check(iconPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val cashBackOwnerPresetError =
        check(this.cashBackOwnerPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }

    val cashBackOwnerPresetEnabled = disabledCashBackOwnerPresetValue == null

    fun makeResult(cashBackPresetId: String? = null): CashBackPreset? =
        listOf(nameError.value, iconError.value, cashBackOwnerPresetError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                CashBackPreset(
                    id = cashBackPresetId ?: "",
                    name = name.value,
                    info = info.value,
                    iconPreset = iconPreset.value!!,
                    cashBackOwnerPreset = cashBackOwnerPreset.value!!,
                    mccCodes = mccCodes.value,
                )
            }

    fun ui(block: SaveCashBackPresetForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }
}

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    DUPLICATE,
}
