package su.tease.project.feature.preset.data.dataSource

import retrofit2.http.GET
import su.tease.project.feature.preset.data.dataSource.dto.BankIconPresetDto
import su.tease.project.feature.preset.data.dataSource.dto.BankPresetDto
import su.tease.project.feature.preset.data.dataSource.dto.CashBackIconPresetDto
import su.tease.project.feature.preset.data.dataSource.dto.MccCodePresetDto
import su.tease.project.feature.preset.data.dataSource.dto.PresetVersionDto
import su.tease.project.feature.preset.data.dataSource.dto.ShopIconPresetDto
import su.tease.project.feature.preset.data.dataSource.dto.ShopPresetDto

interface PresetDataSource {

    @GET("api/v3/dictionary/version")
    suspend fun version(): PresetVersionDto

    @GET("api/v3/dictionary/bank/list")
    suspend fun banks(): List<BankPresetDto>

    @GET("api/v3/dictionary/shop/list")
    suspend fun shops(): List<ShopPresetDto>

    @GET("api/v3/dictionary/icon/bank")
    suspend fun bankIcons(): List<BankIconPresetDto>

    @GET("api/v3/dictionary/icon/shop")
    suspend fun shopIcons(): List<ShopIconPresetDto>

    @GET("api/v3/dictionary/icon/cashBack")
    suspend fun cashBackIcons(): List<CashBackIconPresetDto>

    @GET("api/v3/dictionary/mcc/list")
    suspend fun mccCodes(): List<MccCodePresetDto>
}
