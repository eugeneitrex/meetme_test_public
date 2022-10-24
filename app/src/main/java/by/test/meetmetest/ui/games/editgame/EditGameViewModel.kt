package by.test.meetmetest.ui.games.editgame

import by.test.meetmetest.repo.Repository
import by.test.meetmetest.ui.games.addgame.AddGameViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditGameViewModel @Inject constructor(private val repository: Repository) :
    AddGameViewModel(repository) {
    override fun saveGame() {
        if (checkAllCorrect()) {
            disposableBag.add(
                repository.updateGame(
                    createPlayer(true), createPlayer(false), gameForEdit
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        actionLiveData.value = Action.NavigateBack
                    }, {
                        it.printStackTrace()
                    })
            )
        }
    }
}
