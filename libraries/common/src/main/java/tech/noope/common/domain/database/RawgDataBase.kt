package tech.noope.common.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.noope.common.domain.data.GameDataModel

@Database(
    entities = [GameDataModel::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converter::class)
abstract class RawgDataBase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}