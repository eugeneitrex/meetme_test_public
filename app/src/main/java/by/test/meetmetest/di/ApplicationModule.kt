package by.test.meetmetest.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.test.meetmetest.ui.games.GamesViewModel
import by.test.meetmetest.ui.games.addgame.AddGameViewModel
import by.test.meetmetest.ui.games.editgame.EditGameViewModel
import by.test.meetmetest.ui.top.topplayed.TopPlayedViewModel
import by.test.meetmetest.ui.top.topwinners.TopWinnersViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ApplicationModule {
    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindBaseViewModelFactory(baseViewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    abstract fun bindGamesViewModel(gamesViewModel: GamesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddGameViewModel::class)
    abstract fun bindAddGameViewModel(addGameViewModel: AddGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditGameViewModel::class)
    abstract fun bindEditGameViewModel(editGameViewModel: EditGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopWinnersViewModel::class)
    abstract fun bindTopWinnersViewModel(topWinnersViewModel: TopWinnersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopPlayedViewModel::class)
    abstract fun bindTopPlayedViewModel(topPlayedViewModel: TopPlayedViewModel): ViewModel
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val viewModelClass: KClass<out ViewModel>)
