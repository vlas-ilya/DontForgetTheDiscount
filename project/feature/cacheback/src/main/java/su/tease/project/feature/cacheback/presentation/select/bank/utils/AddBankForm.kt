package su.tease.project.feature.cacheback.presentation.select.bank.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddBankError

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    DUPLICATE,
}

class AddBankForm {
    private val wasValidation = mutableStateOf(false)

    val name = mutableStateOf("")
    val icon = mutableStateOf<IconPreset?>(null)
    val error = mutableStateOf<AddBankError?>(null)

    val nameError = check(name) {
        require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY)
        require(error.value == AddBankError.DUPLICATE_ERROR, FormFieldError.DUPLICATE)
    }
    val iconError = check(icon) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }

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
                    icon = icon.value!!,
                )
            }

    fun ui(block: AddBankForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        name.value = ""
        icon.value = null
    }
}
