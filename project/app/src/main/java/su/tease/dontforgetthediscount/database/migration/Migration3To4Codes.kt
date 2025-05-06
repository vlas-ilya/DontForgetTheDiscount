@file:Suppress("MagicNumber")

package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration3to4Codes = migration(3, 4) {
    execSQL(CREATE_TABLE_CODE_PRESET_ENTITY.trimIndent())
}

private const val CREATE_TABLE_CODE_PRESET_ENTITY = """
    CREATE TABLE `CacheBackCodePresetEntity` (
        `id` varchar(40),
        `code` varchar(10),
        `usageCount` integer
    );
    
    CREATE INDEX index_CacheBackCodePresetEntity_code
    ON CacheBackCodePresetEntity (code);
"""
