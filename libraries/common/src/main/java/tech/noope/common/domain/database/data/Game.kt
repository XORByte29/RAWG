package tech.noope.common.domain.database.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import tech.noope.common.domain.database.DatabaseConst
import tech.noope.common.domain.data.GameDataModel

@Entity(tableName = DatabaseConst.Table.GAME_TABLE)
data class Game(
    @PrimaryKey(autoGenerate = true)
    var gameId: Int,

    @SerializedName("game_data")
    @Embedded(prefix = "data_")
    val data: GameDataModel
) {
    constructor() : this(0, GameDataModel())
}