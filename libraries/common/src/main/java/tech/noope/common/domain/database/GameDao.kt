package tech.noope.common.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import tech.noope.common.domain.database.table.GameDataTable

@Dao
interface GameDao {

    @Query("SELECT * FROM ${DatabaseConst.Table.GAME_TABLE}")
    fun getGames(): List<GameDataTable>

    @Query("SELECT * FROM ${DatabaseConst.Table.GAME_TABLE} WHERE id = :id")
    fun getGame(id: Int): GameDataTable

    @Insert(onConflict = IGNORE)
    fun saveToFavorite(game: GameDataTable): Long

    @Delete
    fun removeFromFavorite(game: GameDataTable): Int
}