package tech.noope.common.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import tech.noope.common.domain.data.GameDataModel

@Dao
interface GameDao {

    @Query("SELECT * FROM ${DatabaseConst.Table.GAME_TABLE}")
    fun getGames(): List<GameDataModel>

    @Query("SELECT * FROM ${DatabaseConst.Table.GAME_TABLE} WHERE id = :id")
    fun getGame(id: Int): GameDataModel

    @Insert(onConflict = IGNORE)
    fun saveToFavorite(game: GameDataModel): Long

    @Delete
    fun removeFromFavorite(game: GameDataModel): Int
}