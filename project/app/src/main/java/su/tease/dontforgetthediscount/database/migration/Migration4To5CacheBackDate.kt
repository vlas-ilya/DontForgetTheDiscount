@file:Suppress("MagicNumber")

package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration4To5CacheBackDate = migration(4, 5) {
    execSQL(ALTER_TABLE_CACHE_BACK_ENTITY_ADD_DATE.trimIndent())
}

private const val ALTER_TABLE_CACHE_BACK_ENTITY_ADD_DATE = """
    ALTER TABLE `CacheBackEntity`
    ADD COLUMN cacheBackMonth integer;
    
    ALTER TABLE `CacheBackEntity`
    ADD COLUMN cacheBackYear integer;
    
    UPDATE `CacheBackEntity` SET cacheBackMonth = 4, cacheBackYear = 2025;
    
    CREATE INDEX index_CacheBackEntity_cacheBackMonth_cacheBackYear
    ON CacheBackEntity (cacheBackMonth ASC, cacheBackYear ASC);
"""
