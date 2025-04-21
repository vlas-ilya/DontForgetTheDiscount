package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dataSource.dto.CacheBackIconDto
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIcon

fun CacheBackIconDto.toDomain() = CacheBackIcon(
    url = url,
)
