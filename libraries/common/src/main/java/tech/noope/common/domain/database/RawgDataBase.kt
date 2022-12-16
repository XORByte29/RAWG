package tech.noope.common.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.noope.common.domain.database.table.GameDataTable

@Database(
    entities = [GameDataTable::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converter::class)
abstract class RawgDataBase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}