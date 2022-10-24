package by.test.meetmetest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.test.meetmetest.data.entities.Player
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PlayerDao {
    @Query("SELECT * FROM ${Player.TABLE_NAME}")
    fun getAllPlayers(): Single<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayers(players: List<Player>): Completable

    @Query("SELECT * FROM ${Player.TABLE_NAME} WHERE id = :playerId")
    fun getPlayerById(playerId: String): Player
}
