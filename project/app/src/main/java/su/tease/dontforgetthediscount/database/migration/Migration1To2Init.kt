package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration1to2Init = migration(1, 2) {
    execSQL(CreateTableCacheBackCodeEntity.trimIndent())
    execSQL(CreateTableCacheBackEntity.trimIndent())
    execSQL(CreateTableCacheBackBankEntity.trimIndent())
}

private const val CreateTableCacheBackCodeEntity = """
    CREATE TABLE `CacheBackCodeEntity` (
        `id` varchar(40),
        `code` varchar(10),
        `cacheBackId` varchar(40),
        FOREIGN KEY (cacheBackId)  REFERENCES CacheBackEntity (id)
    );
    
    CREATE INDEX index_CacheBackCodeEntity_code
    ON CacheBackCodeEntity (code);
    
    CREATE INDEX index_CacheBackCodeEntity_cacheBackId
    ON CacheBackCodeEntity (cacheBackId);
"""

private const val CreateTableCacheBackEntity = """
    CREATE TABLE `CacheBackEntity` (
        `id` varchar(40),
        `name` varchar(255),
        `info` varchar(255),
        `iconUrl` varchar(255),
        `size` integer,
        `cacheBackBankId` varchar(40),
        FOREIGN KEY (cacheBackBankId)  REFERENCES CacheBackBankEntity (id)
    );
    
    CREATE INDEX index_CacheBackEntity_cacheBackBankId
    ON CacheBackCodeEntity (cacheBackBankId);
"""

private const val CreateTableCacheBackBankEntity = """
    CREATE TABLE `CacheBackBankEntity` (
        `id` varchar(40),
        `name` varchar(255),
        `iconUrl` varchar(255),
    );
"""
