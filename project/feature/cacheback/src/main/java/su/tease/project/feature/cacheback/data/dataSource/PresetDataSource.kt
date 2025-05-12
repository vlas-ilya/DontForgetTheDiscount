package su.tease.project.feature.cacheback.data.dataSource

import retrofit2.http.GET
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.BankIconPresetDto
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.BankPresetDto
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.CacheBackIconPresetDto
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.MccCodePresetDto
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.PresetVersionDto

interface PresetDataSource {

    @GET("api/v2/dictionary/version")
    suspend fun version(): PresetVersionDto

    @GET("api/v2/dictionary/bank/list")
    suspend fun banks(): List<BankPresetDto>

    @GET("api/v2/dictionary/icon/bank")
    suspend fun bankIcons(): List<BankIconPresetDto>

    @GET("api/v2/dictionary/icon/cacheBack")
    suspend fun cacheBackIcons(): List<CacheBackIconPresetDto>

    @GET("api/v2/dictionary/mcc/list")
    suspend fun mccCodes(): List<MccCodePresetDto>
}
