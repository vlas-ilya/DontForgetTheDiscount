package su.tease.project.feature.cacheback.presentation.add.utls

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackRequest

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    INCORRECT_VALUE,
}

@Stable
class AddCacheBackForm {
    private val wasValidation = mutableStateOf(false)

    val bank = mutableStateOf<BankPreset?>(null)
    val name = mutableStateOf(CacheBackName(""))
    val info = mutableStateOf(CacheBackInfo(""))
    val icon = mutableStateOf<IconPreset?>(null)
    val size = mutableStateOf(CacheBackSize(0))
    val codes = mutableStateOf(persistentListOf<CacheBackCode>())
    val addMore = mutableStateOf(false)

    val bankError = check(bank) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val nameError = check(name) { require(it.value.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY) }
    val iconError = check(icon) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val sizeError = check(size) { require(it.percent !in INTERVAL, FormFieldError.INCORRECT_VALUE) }

    fun makeResult(): AddCacheBackRequest? =
        listOf(bankError.value, nameError.value, iconError.value, sizeError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                AddCacheBackRequest(
                    bank = bank.value!!,
                    name = name.value,
                    info = info.value,
                    icon = icon.value!!,
                    size = size.value,
                    codes = codes.value,
                )
            }

    fun ui(block: AddCacheBackForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        bank.value = null
        name.value = CacheBackName("")
        info.value = CacheBackInfo("")
        icon.value = null
        size.value = CacheBackSize(0)
        codes.value = persistentListOf()
    }
}

@Suppress("MagicNumber")
private val INTERVAL = 1..100
