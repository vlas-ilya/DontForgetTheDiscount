package su.tease.project.feature.preset.presentation.shop.save.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import su.tease.project.core.utils.checker.check
import su.tease.project.core.utils.ext.runIf
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetError

class SaveShopPresetForm(
    private val initShopPreset: ShopPreset?
) {
    private val wasValidation = mutableStateOf(false)

    val name = mutableStateOf(initShopPreset?.name ?: "")
    val iconPreset = mutableStateOf(initShopPreset?.iconPreset)
    val error = mutableStateOf<SaveShopPresetError?>(null)

    val nameError = check(name) {
        require(it.isBlank(), FormFieldError.REQUIRED_BUT_EMPTY)
        require(error.value == SaveShopPresetError.DUPLICATE_ERROR, FormFieldError.DUPLICATE)
    }
    val iconError = check(iconPreset) { require(it == null, FormFieldError.REQUIRED_BUT_EMPTY) }

    fun setName(value: String) {
        name.value = value
        error.value = null
    }

    fun makeResult(): ShopPreset? =
        listOf(nameError.value, iconError.value)
            .all { it == null }
            .also { wasValidation.value = !it }
            .takeIf { it }
            ?.let {
                ShopPreset(
                    id = initShopPreset?.id ?: "",
                    name = name.value,
                    iconPreset = iconPreset.value!!,
                )
            }

    fun ui(block: SaveShopPresetForm.() -> State<FormFieldError?>): State<FormFieldError?> =
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
