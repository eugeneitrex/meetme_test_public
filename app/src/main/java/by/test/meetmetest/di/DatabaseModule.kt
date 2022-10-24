package by.test.meetmetest.di

import android.content.Context
import androidx.room.Room
import by.test.meetmetest.db.AppDataBase
import by.test.meetmetest.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java, Constants.DATABASE_NAME
        ).build()
    }
}
