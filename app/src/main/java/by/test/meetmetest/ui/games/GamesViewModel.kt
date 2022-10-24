package by.test.meetmetest.ui.games

import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.test.meetmetest.R
import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.repo.Repository
import by.test.meetmetest.ui.BaseViewModel
import by.test.meetmetest.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class GamesViewModel @Inject constructor(private val repository: Repository) : BaseViewModel(),
    DefaultLifecycleObserver {
    private val _gamesLiveData = MutableLiveData<ArrayList<Game>>()
    val gamesLiveData: LiveData<ArrayList<Game>> = _gamesLiveData
    var isBusy = MutableLiveData(false)

    override fun onResume(owner: LifecycleOwner) {
        super<BaseViewModel>.onResume(owner)
        getGames()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposableBag.clear()
        super<BaseViewModel>.onDestroy(owner)
    }

    fun onAddGameClicked() {
        navigate(R.id.nav_add_game)
    }

    fun deleteGameClicked(game: Game) {
        disposableBag.add(
            repository.deleteGame(game)
                .subscribeOn(Schedulers.io())
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe()
        )
    }

    fun editGameClicked(game: Game) {
        navigate(R.id.nav_edit_game, Bundle().apply {
            putSerializable(Constants.GAME_EDIT, game)
        })
    }

    private fun getGames() {
        disposableBag.add(
            repository.getAllGames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _gamesLiveData.value = it as ArrayList
                }, {
                    Timber.d("error getGames(): ${it.localizedMessage}")
                })
        )
    }
}
