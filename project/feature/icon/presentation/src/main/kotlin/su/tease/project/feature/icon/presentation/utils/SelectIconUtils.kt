package su.tease.project.feature.icon.presentation.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import su.tease.project.core.utils.utils.rememberCallback

@Composable
fun rememberIconSelector(): Triple<MutableState<Uri?>, () -> Unit, () -> Unit> {
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it ?: return@rememberLauncherForActivityResult
        selectedImageUri.value = it
    }

    val onSelect = rememberCallback(pickMedia) {
        pickMedia.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    val onClean = rememberCallback(pickMedia) {
        selectedImageUri.value = null
    }

    return Triple(selectedImageUri, onSelect, onClean)
}