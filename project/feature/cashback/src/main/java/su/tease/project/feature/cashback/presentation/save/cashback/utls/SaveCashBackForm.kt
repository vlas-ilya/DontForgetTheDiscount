package su.tease.project.feature.cashback.presentation.save.cashback.utls

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackRequest
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset

@Stable
class SaveCashBackForm(
    bankAccountValue: BankAccount?,
    cashBackPresetValue: CashBackPreset?,
    sizeValue: Int?,
    private val dateValue: CashBackDate
) {
    private val wasValidation = mutableStateOf(false)

    val bankAccount = mutableStateOf(bankAccountValue)
    val cashBackPreset = mutableStateOf(cashBackPresetValue)
    val size = mutableStateOf(sizeValue)
    val date = mutableStateOf(dateValue)
    val addMore = mutableStateOf(false)

    val bankAccountError = check(bankAccount) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val cashBackPresetError = check(cashBackPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val sizeError = check(size) { require(it !in INTERVAL, FormFieldError.INCORRECT_VALUE) }

    val cashBackPresetEnabled = derivedStateOf { bankAccount.value != null }

    fun makeResult(cashBackId: String? = null): SaveCashBackRequest? =
        listOf(bankAccountError.value, cashBackPresetError.value, sizeError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                SaveCashBackRequest(
                    id = cashBackId,
                    bankAccount = bankAccount.value,
                    cashBackPreset = cashBackPreset.value,
                    size = size.value,
                    date = date.value,
                )
            }

    fun ui(block: SaveCashBackForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        bankAccount.value = null
        cashBackPreset.value = null
        size.value = null
        date.value = dateValue
    }
}

@Suppress("MagicNumber")
private val INTERVAL = 1..10000

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    INCORRECT_VALUE,
}
