package by.test.meetmetest.ui.games.addgame

import androidx.lifecycle.MutableLiveData
import by.test.meetmetest.R
import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.repo.Repository
import by.test.meetmetest.ui.BaseViewModel
import by.test.meetmetest.utils.Constants.Companion.FIRST_PLAYER
import by.test.meetmetest.utils.Constants.Companion.SECOND_PLAYER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

open class AddGameViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {
    //actions
    val actionLiveData = MutableLiveData<Action>()

    //data for 1st player
    val p1NameLiveData = MutableLiveData<String>()
    val p1IdLiveData = MutableLiveData("")
    val p1ScoreLiveData = MutableLiveData<String>()

    //data for 2nd player
    val p2NameLiveData = MutableLiveData<String>()
    val p2IdLiveData = MutableLiveData("")
    val p2ScoreLiveData = MutableLiveData<String>()

    lateinit var gameForEdit: Game

    private lateinit var player1: Player
    private lateinit var player2: Player

    fun onSaveGameClicked() {
        saveGame()
    }

    open fun saveGame() {
        if (checkAllCorrect()) {
            disposableBag.add(
                repository.createGame(
                    createPlayer(true), createPlayer(false)
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

    fun createPlayer(isFirst: Boolean): Player {
        return if (isFirst) {
            //save score
            p1ScoreLiveData.value?.let {
                player1.score = it.toInt()
            }
            player1

        } else {
            p2ScoreLiveData.value?.let {
                player2.score = it.toInt()
            }
            player2
        }
    }

    fun onChoosePlayerClicked(isFirstPlayer: Boolean) {
        disposableBag.add(
            repository.getAllPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    actionLiveData.value = Action.ShowListOfPlayers(
                        it,
                        isFirstPlayer
                    )
                }, {
                    Timber.d("error onPlayerClicked(): ${it.localizedMessage}")
                    it.printStackTrace()
                })
        )
    }

    /**
     * Check if all input fields are correct
     */
    open fun checkAllCorrect(): Boolean {
        //check name 1
        if (p1NameLiveData.value.isNullOrEmpty()) {
            actionLiveData.value = Action.ShowError(R.string.add_game_error_name_1)
            return false
        }

        //check score 1
        if (p1ScoreLiveData.value.isNullOrEmpty()) {
            actionLiveData.value = Action.ShowError(R.string.add_game_error_score_1)
            return false
        }

        //check name 2
        if (p2NameLiveData.value.isNullOrEmpty()) {
            actionLiveData.value = Action.ShowError(R.string.add_game_error_name_2)
            return false
        }

        //check score 2
        if (p2ScoreLiveData.value.isNullOrEmpty()) {
            actionLiveData.value = Action.ShowError(R.string.add_game_error_score_2)
            return false
        }

        //check equal players
        if (player1.id == player2.id) {
            actionLiveData.value = Action.ShowError(R.string.add_game_error_players_equals)
            return false
        }

        //check equal score
        val score1: Int? = p1ScoreLiveData.value?.toIntOrNull()
        val score2: Int? = p2ScoreLiveData.value?.toIntOrNull()
        if (score1 == score2) {
            actionLiveData.value = Action.ShowError(R.string.add_game_error_score_equals)
            return false
        }

        //all correct
        return true
    }

    /**
     * player has been selected from list of available players
     */
    fun playerSelected(player: Player, isFirstPlayer: Boolean) {
        if (isFirstPlayer) {
            player1 = player
            p1NameLiveData.value = player.name
            p1IdLiveData.value = player.id
        } else {
            player2 = player
            p2NameLiveData.value = player.name
            p2IdLiveData.value = player.id
        }
    }

    fun setGameToEdit(gameToEdit: Game) {
        gameForEdit = gameToEdit

        player1 = gameToEdit.players[FIRST_PLAYER]
        player2 = gameToEdit.players[SECOND_PLAYER]

        p1NameLiveData.value = player1.name
        p1IdLiveData.value = player1.id
        p1ScoreLiveData.value = player1.score.toString()

        p2NameLiveData.value = player2.name
        p2IdLiveData.value = player2.id
        p2ScoreLiveData.value = player2.score.toString()
    }

    sealed class Action {
        data class ShowListOfPlayers(val list: List<Player>, val isFirstPlayer: Boolean) : Action()
        data class ShowError(val errorStringId: Int) : Action()
        object NavigateBack : Action()
    }
}
