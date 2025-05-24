package su.tease.project.feature.cashback.presentation.save.bank.save.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.preset.api.domain.entity.BankPreset

class SaveBankAccountForm(
    bankPresetValue: BankPreset?,
    customNameValue: String,
) {
    private val wasValidation = mutableStateOf(false)

    val bankPreset = mutableStateOf(bankPresetValue)
    val customName = mutableStateOf(customNameValue)

    val bankPresetError = check(bankPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val customNameError = check(customName) { require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY) }

    fun makeResult(bankAccountId: String? = null): BankAccount? =
        listOf(bankPresetError.value, customNameError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                BankAccount(
                    id = bankAccountId ?: "",
                    bankPreset = bankPreset.value!!,
                    customName = customName.value,
                    cashBacks = persistentListOf(),
                )
            }

    fun ui(block: SaveBankAccountForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        bankPreset.value = null
        customName.value = ""
    }
}

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    INCORRECT_VALUE,
}
