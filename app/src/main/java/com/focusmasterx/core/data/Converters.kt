package com.focusmasterx.core.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter fun fromStrings(value: List<String>): String = value.joinToString("\u001F")
    @TypeConverter fun toStrings(value: String): List<String> = if (value.isBlank()) emptyList() else value.split("\u001F")
    @TypeConverter fun fromBlockType(value: BlockType): String = value.name
    @TypeConverter fun toBlockType(value: String): BlockType = BlockType.valueOf(value)
}
