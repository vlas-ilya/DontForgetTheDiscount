package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration1to2Init = migration(1, 2) {
    execSQL(CREATE_TABLE_BANK_ICON_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_BANK_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CACHE_BACK_ICON_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_MCC_CODE_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CACHE_BACK_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CACHE_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_PRESETS_VERSION_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_BANK_ACCOUNT_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CACHE_BACK_ENTITY.trimIndent())
}

private const val CREATE_TABLE_BANK_ICON_PRESET_ENTITY = """
    CREATE TABLE `BankIconPresetEntity` (
        `id` varchar(40) PRIMARY KEY,
        `iconUrl` varchar(255),
    );
"""

private const val CREATE_TABLE_BANK_PRESET_ENTITY = """
    CREATE TABLE `BankPresetEntity` (
        `id` varchar(40) PRIMARY KEY,
        `name` varchar(255),
        `iconPresetId` varchar(40),
        
        FOREIGN KEY (iconPresetId) REFERENCES BankIconPresetEntity (id)
    );
    
    CREATE INDEX index_BankPresetEntity_iconPresetId
    ON BankPresetEntity (iconPresetId);
"""

private const val CREATE_TABLE_CACHE_BACK_ICON_PRESET_ENTITY = """
    CREATE TABLE `CacheBackIconPresetEntity` (
        `id` varchar(40) PRIMARY KEY,
        `iconUrl` varchar(255),
    );
"""

private const val CREATE_TABLE_MCC_CODE_PRESET_ENTITY = """
    CREATE TABLE `MccCodePresetEntity` (
        `id` varchar(40) PRIMARY KEY,
        `code` varchar(4),
        `name` varchar(255),
        `info` text,
    );
"""

private const val CREATE_TABLE_CACHE_BACK_PRESET_ENTITY = """
    CREATE TABLE `CacheBackPresetEntity` (
        `id` varchar(40) PRIMARY KEY,
        `code` varchar(255),
        `info` varchar(255),
        `iconPresetId` varchar(40),
        `bankPresetId` varchar(40),
        
        FOREIGN KEY (iconPresetId) REFERENCES CacheBackIconPresetEntity (id),
        FOREIGN KEY (bankPresetId) REFERENCES BankIconPresetEntity (id)
    );
        
    CREATE INDEX index_CacheBackPresetEntity_iconPresetId
    ON CacheBackPresetEntity (iconPresetId);
        
    CREATE INDEX index_CacheBackPresetEntity_bankPresetId
    ON CacheBackPresetEntity (bankPresetId);
"""

private const val CREATE_TABLE_CACHE_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY = """
    CREATE TABLE `CacheBackPresetToMccCodePresetEntity` (
        `cacheBackPresetId` varchar(40),
        `mccCodePresetId` varchar(40),
        
        FOREIGN KEY (cacheBackPresetId) REFERENCES CacheBackPresetEntity (id),
        FOREIGN KEY (mccCodePresetId) REFERENCES MccCodePresetEntity (id)
    );
    
    CREATE INDEX index_CacheBackPresetToMccCodePresetEntity_cacheBackPresetId
    ON CacheBackPresetToMccCodePresetEntity (cacheBackPresetId);
        
    CREATE INDEX index_CacheBackPresetToMccCodePresetEntity_mccCodePresetId
    ON CacheBackPresetToMccCodePresetEntity (mccCodePresetId);
"""

private const val CREATE_TABLE_PRESETS_VERSION_ENTITY = """
    CREATE TABLE `PresetsVersionEntity` (
        `id` varchar(40) PRIMARY KEY,
        `banks` INTEGER,
        `bankIcons` INTEGER,
        `cacheBackIcons` INTEGER,
        `mccCodes` INTEGER,
    );
"""

private const val CREATE_TABLE_BANK_ACCOUNT_ENTITY = """
    CREATE TABLE `BankAccountEntity` (
        `id` varchar(40) PRIMARY KEY,
        `bankPresetId` varchar(40),
        `customName` varchar(255),
        
        FOREIGN KEY (bankPresetId) REFERENCES BankPresetEntity (id)
    );
    
    CREATE INDEX index_BankAccountEntity_bankPresetId
    ON BankAccountEntity (bankPresetId);
"""

private const val CREATE_TABLE_CACHE_BACK_ENTITY = """
    CREATE TABLE `CacheBackEntity` (
        `id` varchar(40) PRIMARY KEY,
        `cacheBackPresetId` varchar(40),
        `size` INTEGER,
        `cacheBackMonth` INTEGER,
        `cacheBackYear` INTEGER,
        `bankAccountId` varchar(40),
        
        FOREIGN KEY (cacheBackPresetId) REFERENCES CacheBackPresetEntity (id)
        FOREIGN KEY (bankAccountId) REFERENCES BankAccountEntity (id)
    );
    
    CREATE INDEX index_CacheBackEntity_cacheBackPresetId
    ON CacheBackEntity (cacheBackPresetId);
    
    CREATE INDEX index_CacheBackEntity_bankAccountId
    ON CacheBackEntity (bankAccountId);
"""
