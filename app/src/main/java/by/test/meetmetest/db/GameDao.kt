package by.test.meetmetest.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.test.meetmetest.data.entities.Game
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface GameDao {
    @Query("SELECT * FROM ${Game.TABLE_NAME_GAME}")
    fun getAllGames(): Observable<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<Game>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(games: Game): Completable

    @Delete
    fun deleteGame(game: Game): Completable

    @Update
    fun updateGame(gameForEdit: Game): Completable
}
