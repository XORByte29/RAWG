package tech.noope.common.domain.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import tech.noope.common.domain.data.Publisher
import tech.noope.common.domain.database.DatabaseConst

@Entity(tableName = DatabaseConst.Table.GAME_TABLE)
data class GameDataTable(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = 0,
    @SerializedName("slug")
    val slug: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("description_raw")
    val description: String? = "",
    @SerializedName("released")
    val released: String? = "",
    @SerializedName("background_image")
    val image: String? = "",
    @SerializedName("rating")
    val rating: Double? = 0.0,
    @SerializedName("playtime")
    val playtime: Double? = 0.0,
    @SerializedName("publishers")
    val publishers: List<Publisher>? = listOf(),
    @SerializedName("developers")
    val developers: List<Publisher>? = listOf(),
)