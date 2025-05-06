package su.tease.project.feature.cacheback.presentation.save.utls

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackRequest

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    INCORRECT_VALUE,
}

@Stable
class SaveCacheBackForm(
    bankValue: BankPreset?,
    nameValue: CacheBackName,
    infoValue: CacheBackInfo,
    iconValue: IconPreset?,
    sizeValue: CacheBackSize,
    codesValue: PersistentList<CacheBackCode>,
    private val dateValue: CacheBackDate
) {
    private val wasValidation = mutableStateOf(false)

    val bank = mutableStateOf(bankValue)
    val name = mutableStateOf(nameValue)
    val info = mutableStateOf(infoValue)
    val icon = mutableStateOf(iconValue)
    val size = mutableStateOf(sizeValue)
    val codes = mutableStateOf(codesValue)
    val date = mutableStateOf(dateValue)
    val addMore = mutableStateOf(false)

    val bankError = check(bank) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val nameError = check(name) { require(it.value.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY) }
    val iconError = check(icon) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val sizeError = check(size) { require(it.percent !in INTERVAL, FormFieldError.INCORRECT_VALUE) }

    fun makeResult(id: CacheBackId? = null): SaveCacheBackRequest? =
        listOf(bankError.value, nameError.value, iconError.value, sizeError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                SaveCacheBackRequest(
                    id = id,
                    bank = bank.value!!,
                    name = name.value,
                    info = info.value,
                    icon = icon.value!!,
                    size = size.value,
                    codes = codes.value,
                    date = date.value,
                )
            }

    fun ui(block: SaveCacheBackForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        bank.value = null
        date.value = dateValue
        name.value = CacheBackName("")
        info.value = CacheBackInfo("")
        icon.value = null
        size.value = CacheBackSize(0)
        codes.value = persistentListOf()
    }
}

@Suppress("MagicNumber")
private val INTERVAL = 1..100
