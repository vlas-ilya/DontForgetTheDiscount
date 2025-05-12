package su.tease.project.feature.cacheback.data.dataSource.dto.preset

import kotlinx.serialization.Serializable

@Serializable
data class PresetVersionDto(
    val banks: Int,
    val bankIcons: Int,
    val cacheBackIcons: Int,
    val mccCodes: Int,
)
