package by.test.meetmetest.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.test.meetmetest.data.entities.Game.Companion.TABLE_NAME_GAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME_GAME)
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "date")
    @SerializedName("date")
    val date: String,

    @ColumnInfo(name = "winner_id")
    @SerializedName("winner_id")
    var winnerId: String,

    @ColumnInfo(name = "players")
    @SerializedName("players")
    val players: ArrayList<Player>
) : java.io.Serializable {
    companion object {
        const val TABLE_NAME_GAME = "game"
    }
}