package su.tease.project.feature.shop.presentation.save.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.entity.ShopPreset

class SaveShopForm(
    cashBackOwnerPresetValue: ShopPreset?,
    customNameValue: String,
) {
    private val wasValidation = mutableStateOf(false)

    val shopPreset = mutableStateOf(cashBackOwnerPresetValue)
    val customName = mutableStateOf(customNameValue)

    val shopPresetError = check(shopPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }
    val customNameError = check(customName) { require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY) }

    fun makeResult(shopId: String? = null): Shop? =
        listOf(shopPresetError.value, customNameError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                Shop(
                    id = shopId ?: "",
                    preset = shopPreset.value!!,
                    customName = customName.value,
                    cashBacks = persistentListOf(),
                )
            }

    fun ui(block: SaveShopForm.() -> State<FormFieldError?>): State<FormFieldError?> =
        derivedStateOf { runIf(wasValidation.value) { block().value } }

    fun clean() {
        shopPreset.value = null
        customName.value = ""
    }
}

enum class FormFieldError {
    REQUIRED_BUT_EMPTY,
    INCORRECT_VALUE,
}
