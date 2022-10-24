package by.test.meetmetest.di

import by.test.meetmetest.ui.top.topplayed.TopPlayedFragment
import by.test.meetmetest.ui.top.topwinners.TopWinnersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PlayersModule {
    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideTopWinnersFragment(): TopWinnersFragment

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideTopPlayedFragment(): TopPlayedFragment
}
