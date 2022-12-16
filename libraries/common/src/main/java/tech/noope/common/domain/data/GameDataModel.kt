package tech.noope.common.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import tech.noope.common.domain.database.DatabaseConst

@Entity(tableName = DatabaseConst.Table.GAME_TABLE)
data class GameDataModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("slug")
    val slug: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description_raw")
    val description: String = "",
    @SerializedName("released")
    val released: String = "",
    @SerializedName("background_image")
    val image: String = "",
    @SerializedName("rating")
    val rating: Double = 0.0,
    @SerializedName("playtime")
    val playtime: Double = 0.0,
    @SerializedName("publishers")
    val publishers: List<Publisher> = listOf(),
    @SerializedName("developers")
    val developers: List<Publisher> = listOf(),
) {
    constructor(): this(0)
}

data class Publisher(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
)

fun createGameDataPreview() = GameDataModel(
    id = 0,
    name = "Dummy Game",
    description = "Lorem ipsum dollor sit amet",
    released = "11-11-2022",
    image = "https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg",
    playtime = 1000.0,
    publishers = listOf(
        Publisher(
            id = 0,
            name = "Noope",
        )
    )
)