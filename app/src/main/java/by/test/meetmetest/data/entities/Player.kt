package by.test.meetmetest.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.test.meetmetest.data.entities.Player.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME)
data class Player(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "is_winner")
    @SerializedName("is_winner")
    var isWinner: Boolean,

    @ColumnInfo(name = "score")
    @SerializedName("score")
    var score: Int,

    @ColumnInfo(name = "totalWins")
    var totalWins: Int = 0,

    @ColumnInfo(name = "totalGames")
    var totalGames: Int = 0
) : java.io.Serializable {
    companion object {
        const val TABLE_NAME = "player"
    }
}