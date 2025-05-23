package su.tease.project.feature.cacheback.presentation.save.cacheback.utls

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackRequest
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

@Stable
class SaveCacheBackForm(
    bankAccountValue: BankAccount?,
    cacheBackPresetValue: CacheBackPreset?,
    sizeValue: Int?,
    private val dateValue: CacheBackDate
) {
    private val wasValidation = mutableStateOf(false)

    val bankAccount = mutableStateOf(bankAccountValue)
    val cacheBackPreset = mutableStateOf(cacheBackPresetValue)
    val size = mutableStateOf(sizeValue)
    val date = mutableStateOf(dateValue)
    val addMore = mutableStateOf(false)

    val bankAccountError = check(bankAccount) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val cacheBackPresetError = check(cacheBackPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val sizeError = check(size) { require(it !in INTERVAL, FormFieldError.INCORRECT_VALUE) }

    val cacheBackPresetEnabled = derivedStateOf { bankAccount.value != null }

    fun makeResult(cacheBackId: String? = null): SaveCacheBackRequest? =
        listOf(bankAccountError.value, cacheBackPresetError.value, sizeError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                SaveCacheBackRequest(
                    id = cacheBackId,
                    bankAccount = bankAccount.value,
                    cacheBackPreset = cacheBackPreset.value,
                    size = size.value,
                    date = date.value,
                )
            }

    fun ui(block: SaveCacheBackForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        bankAccount.value = null
        cacheBackPreset.value = null
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
