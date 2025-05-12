package su.tease.project.feature.cacheback.presentation.preset.bank.save.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.presentation.preset.bank.save.action.SaveBankPresetError

class SaveBankPresetForm {
    private val wasValidation = mutableStateOf(false)

    val name = mutableStateOf("")
    val iconPreset = mutableStateOf<BankIconPreset?>(null)
    val error = mutableStateOf<SaveBankPresetError?>(null)

    val nameError = check(name) {
        require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY)
        require(error.value == SaveBankPresetError.DUPLICATE_ERROR, FormFieldError.DUPLICATE)
    }
    val iconError = check(iconPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }

    fun setName(value: String) {
        name.value = value
        error.value = null
    }

    fun makeResult(): BankPreset? =
        listOf(nameError.value, iconError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                BankPreset(
                    id = "",
                    name = name.value,
                    iconPreset = iconPreset.value!!,
                )
            }

    fun ui(block: SaveBankPresetForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        name.value = ""
        iconPreset.value = null
    }
}

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    DUPLICATE,
}
