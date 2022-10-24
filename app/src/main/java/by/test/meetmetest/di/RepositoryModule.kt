package by.test.meetmetest.di

import android.content.Context
import by.test.meetmetest.db.AppDataBase
import by.test.meetmetest.net.Api
import by.test.meetmetest.net.ApiRest
import by.test.meetmetest.repo.Repository
import by.test.meetmetest.repo.RepositoryImpl
import by.test.meetmetest.utils.PreferencesUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(preferencesUtils: PreferencesUtils, api: Api, appDataBase: AppDataBase): Repository {
        return RepositoryImpl(preferencesUtils, api, appDataBase)
    }

    @Provides
    @Singleton
    fun providePreferencesUtils(context: Context): PreferencesUtils {
        return PreferencesUtils(context)
    }

    @Provides
    @Singleton
    fun provideApi(apiRest: ApiRest): Api {
        return apiRest.getApi()
    }

    @Provides
    @Singleton
    fun provideApiRest(): ApiRest {
        return ApiRest()
    }
}
