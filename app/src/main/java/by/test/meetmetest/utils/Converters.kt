package by.test.meetmetest.utils

import androidx.room.TypeConverter
import by.test.meetmetest.data.entities.Player
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: List<Player>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Player>::class.java).toList() as ArrayList
}