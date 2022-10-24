package by.test.meetmetest.di

import androidx.lifecycle.ViewModel
import by.test.meetmetest.MainActivity
import by.test.meetmetest.ui.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            GamesModule::class,
            PlayersModule::class
        ]
    )
    abstract fun provideMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    abstract fun bindViewModel(viewModel: BaseViewModel): ViewModel
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
