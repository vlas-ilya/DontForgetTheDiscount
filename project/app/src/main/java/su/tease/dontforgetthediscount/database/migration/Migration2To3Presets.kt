@file:Suppress("MagicNumber")

package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration2to3Presets = migration(2, 3) {
    execSQL(CREATE_TABLE_BANK_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CACHE_BACK_PRESET_ENTITY.trimIndent())
}

private const val CREATE_TABLE_BANK_PRESET_ENTITY = """
    CREATE TABLE `BankPresetEntity` (
        `id` varchar(40),
        `name` varchar(255),
        `iconUrl` varchar(255)
    );
"""

private const val CREATE_TABLE_CACHE_BACK_PRESET_ENTITY = """
    CREATE TABLE `CacheBackPresetEntity` (
        `id` varchar(40),
        `name` varchar(255),
        `iconUrl` varchar(255)
    );
"""
