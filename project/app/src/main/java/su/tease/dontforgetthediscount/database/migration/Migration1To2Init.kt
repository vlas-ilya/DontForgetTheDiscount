package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration1to2Init = migration(1, 2) {
    execSQL("PRAGMA foreign_keys = ON")
    execSQL(CREATE_TABLE_BANK_ICON_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_BANK_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_BANK_PRESET_ENTITY_INDEX.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_ICON_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_MCC_CODE_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_PRESET_ENTITY_INDEX_1.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_PRESET_ENTITY_INDEX_2.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY_INDEX_1.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY_INDEX_2.trimIndent())
    execSQL(CREATE_TABLE_PRESETS_VERSION_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_BANK_ACCOUNT_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_BANK_ACCOUNT_ENTITY_INDEX.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_ENTITY.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_ENTITY_INDEX_1.trimIndent())
    execSQL(CREATE_TABLE_CASH_BACK_ENTITY_INDEX_2.trimIndent())
}

private const val CREATE_TABLE_BANK_ICON_PRESET_ENTITY = """
    CREATE TABLE `BankIconPresetEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `iconUrl` TEXT NOT NULL
    );
"""

private const val CREATE_TABLE_BANK_PRESET_ENTITY = """
    CREATE TABLE `BankPresetEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `name` TEXT NOT NULL,
        `iconPresetId` TEXT NOT NULL,
        
        FOREIGN KEY (iconPresetId) REFERENCES BankIconPresetEntity (id)
    );
"""

private const val CREATE_TABLE_BANK_PRESET_ENTITY_INDEX = """
    CREATE INDEX index_BankPresetEntity_iconPresetId
    ON BankPresetEntity (iconPresetId);
"""

private const val CREATE_TABLE_CASH_BACK_ICON_PRESET_ENTITY = """
    CREATE TABLE `CashBackIconPresetEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `iconUrl` TEXT NOT NULL,
    );
"""

private const val CREATE_TABLE_MCC_CODE_PRESET_ENTITY = """
    CREATE TABLE `MccCodePresetEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `code` TEXT NOT NULL,
        `name` TEXT NOT NULL,
        `info` TEXT NOT NULL
    );
"""

private const val CREATE_TABLE_CASH_BACK_PRESET_ENTITY = """
    CREATE TABLE `CashBackPresetEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `code` TEXT NOT NULL,
        `info` TEXT NOT NULL,
        `iconPresetId` TEXT NOT NULL,
        `bankPresetId` TEXT NOT NULL,
        
        FOREIGN KEY (iconPresetId) REFERENCES CashBackIconPresetEntity (id),
        FOREIGN KEY (bankPresetId) REFERENCES BankIconPresetEntity (id)
    );   
"""

private const val CREATE_TABLE_CASH_BACK_PRESET_ENTITY_INDEX_1 = """
    CREATE INDEX index_CashBackPresetEntity_iconPresetId
    ON CashBackPresetEntity (iconPresetId);
"""

private const val CREATE_TABLE_CASH_BACK_PRESET_ENTITY_INDEX_2 = """
    CREATE INDEX index_CashBackPresetEntity_bankPresetId
    ON CashBackPresetEntity (bankPresetId);
"""

private const val CREATE_TABLE_CASH_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY = """
    CREATE TABLE `CashBackPresetToMccCodePresetEntity` (
        `cashBackPresetId` TEXT NOT NULL,
        `mccCodePresetId` TEXT NOT NULL,
        
        FOREIGN KEY (cashBackPresetId) REFERENCES CashBackPresetEntity (id),
        FOREIGN KEY (mccCodePresetId) REFERENCES MccCodePresetEntity (id)
    );
"""

private const val CREATE_TABLE_CASH_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY_INDEX_1 = """
    CREATE INDEX index_CashBackPresetToMccCodePresetEntity_cashBackPresetId
    ON CashBackPresetToMccCodePresetEntity (cashBackPresetId);
"""

private const val CREATE_TABLE_CASH_BACK_PRESET_TO_MCC_CODE_PRESET_ENTITY_INDEX_2 = """
    CREATE INDEX index_CashBackPresetToMccCodePresetEntity_mccCodePresetId
    ON CashBackPresetToMccCodePresetEntity (mccCodePresetId);
"""

private const val CREATE_TABLE_PRESETS_VERSION_ENTITY = """
    CREATE TABLE `PresetsVersionEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `banks` INTEGER NOT NULL,
        `bankIcons` INTEGER NOT NULL,
        `cashBackIcons` INTEGER NOT NULL,
        `mccCodes` INTEGER NOT NULL,
    );
"""

private const val CREATE_TABLE_BANK_ACCOUNT_ENTITY = """
    CREATE TABLE `BankAccountEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `bankPresetId` TEXT NOT NULL,
        `customName` TEXT NOT NULL,
        
        FOREIGN KEY (bankPresetId) REFERENCES BankPresetEntity (id)
    );
"""

private const val CREATE_TABLE_BANK_ACCOUNT_ENTITY_INDEX = """
    CREATE INDEX index_BankAccountEntity_bankPresetId
    ON BankAccountEntity (bankPresetId);
"""

private const val CREATE_TABLE_CASH_BACK_ENTITY = """
    CREATE TABLE `CashBackEntity` (
        `id` TEXT PRIMARY KEY NOT NULL,
        `cashBackPresetId` TEXT NOT NULL,
        `size` INTEGER NOT NULL,
        `cashBackMonth` INTEGER NOT NULL,
        `cashBackYear` INTEGER NOT NULL,
        `bankAccountId` TEXT NOT NULL,
        
        FOREIGN KEY (cashBackPresetId) REFERENCES CashBackPresetEntity (id)
        FOREIGN KEY (bankAccountId) REFERENCES BankAccountEntity (id)
    );
"""

private const val CREATE_TABLE_CASH_BACK_ENTITY_INDEX_1 = """
    CREATE INDEX index_CashBackEntity_cashBackPresetId
    ON CashBackEntity (cashBackPresetId);
"""

private const val CREATE_TABLE_CASH_BACK_ENTITY_INDEX_2 = """
    CREATE INDEX index_CashBackEntity_bankAccountId
    ON CashBackEntity (bankAccountId);
"""
