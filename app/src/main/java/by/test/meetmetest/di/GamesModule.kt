package by.test.meetmetest.di

import by.test.meetmetest.ui.games.GamesFragment
import by.test.meetmetest.ui.games.addgame.AddGameFragment
import by.test.meetmetest.ui.games.editgame.EditGameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GamesModule {
    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideGamesFragment(): GamesFragment

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideAddGameFragment(): AddGameFragment

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideEditGameFragment(): EditGameFragment
}
