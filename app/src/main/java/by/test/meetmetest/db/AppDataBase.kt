package by.test.meetmetest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.utils.Converters

@Database(entities = [Player::class, Game::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun gameDao(): GameDao
}
