package by.test.meetmetest.repo

import by.test.meetmetest.db.AppDataBase
import by.test.meetmetest.net.Api
import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.utils.Constants.Companion.FIRST_PLAYER
import by.test.meetmetest.utils.Constants.Companion.SECOND_PLAYER
import by.test.meetmetest.utils.PreferencesUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RepositoryImpl(
    private val prefs: PreferencesUtils,
    private val api: Api,
    private val database: AppDataBase
) : Repository {
    override fun getAllGames(): Observable<List<Game>> {
        return if (prefs.restoreAlreadyLoadedFromBacked()) {
            //return from DB
            database.gameDao().getAllGames()
        } else {
            //return from API
            api.getGames()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    with(it.games) {
                        prefs.saveAlreadyLoadedFromBackend(true)
                        sortByDescending { game ->
                            game.date
                        }
                        saveGamesToDB(this)
                        savePlayersToDB(this)
                        Observable.just(this)
                    }
                }
        }
    }

    override fun deleteGame(game: Game): Completable {
        return database.gameDao().deleteGame(game)
    }

    override fun getAllPlayers(): Single<List<Player>> {
        return database.playerDao().getAllPlayers()
    }

    override fun createGame(player1: Player, player2: Player): Completable {
        player1.isWinner = player1.score > player2.score
        player2.isWinner = player2.score > player1.score

        val newGame = Game(
            id = System.currentTimeMillis().toString(),
            date = (System.currentTimeMillis() / 1000).toString(),
            winnerId = if (player1.score > player2.score) {
                player1.id
            } else {
                player2.id
            },
            players = arrayListOf(player1, player2)
        )
        return database.gameDao().insertGame(newGame)
    }

    override fun updateGame(player1: Player, player2: Player, gameForEdit: Game): Completable {
        with(gameForEdit) {
            players[FIRST_PLAYER] = player1
            players[SECOND_PLAYER] = player2
            winnerId = if (player1.score > player2.score) player1.id else player2.id

            player1.isWinner = player1.score > player2.score
            player2.isWinner = player2.score > player1.score
        }

        return database.gameDao().updateGame(gameForEdit)
    }

    override fun getTopPlayedPlayers(): Observable<List<Player>> {
        return database.gameDao().getAllGames()
            .subscribeOn(Schedulers.io())
            .flatMap {
                val topGamesPlayersIds = HashMap<String, Int>()
                it.forEach { game ->
                    inc(game.players[FIRST_PLAYER].id, topGamesPlayersIds)
                    inc(game.players[SECOND_PLAYER].id, topGamesPlayersIds)
                }

                Observable.just(calculateToppers(topGamesPlayersIds))
            }
    }

    override fun getTopWinPlayers(): Observable<List<Player>> {
        return database.gameDao().getAllGames()
            .subscribeOn(Schedulers.io())
            .flatMap {
                val topWinPlayersIds = HashMap<String, Int>()
                it.forEach { game ->
                    inc(game.winnerId, topWinPlayersIds)
                }

                Observable.just(calculateWinners(topWinPlayersIds))
            }
    }

    private fun calculateWinners(topWinPlayersIds: HashMap<String, Int>): List<Player> {
        val topWinPlayers = ArrayList<Player>()
        topWinPlayersIds.forEach { (playerId, countOfWin) ->
            topWinPlayers.add(getPlayerById(playerId).apply {
                totalWins = countOfWin
            })
        }

        topWinPlayers.sortByDescending { player ->
            player.totalWins
        }
        return topWinPlayers
    }

    private fun calculateToppers(topGamePlayersIds: HashMap<String, Int>): List<Player> {
        val topGamePlayers = ArrayList<Player>()
        topGamePlayersIds.forEach { (playerId, countOfWin) ->
            topGamePlayers.add(getPlayerById(playerId).apply {
                totalGames = countOfWin
            })
        }

        topGamePlayers.sortByDescending { player ->
            player.totalGames
        }
        return topGamePlayers
    }

    /**
     * Increment WIN for current player
     */
    private fun inc(winnerId: String, topWinPlayers: HashMap<String, Int>) {
        topWinPlayers[winnerId] = topWinPlayers[winnerId]?.plus(1) ?: 1
    }

    private fun saveGamesToDB(games: List<Game>) = database.gameDao().insertGames(games)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError {
            Timber.e("error saved in DB: ${it.printStackTrace()}")
        }
        .subscribe()

    private fun savePlayersToDB(games: List<Game>) {
        games.forEach {
            insertPlayer(it)
        }
    }

    private fun insertPlayer(game: Game) = database.playerDao().insertPlayers(game.players)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError { error ->
            error.printStackTrace()
        }
        .subscribe()

    private fun getPlayerById(playerId: String): Player {
        return database.playerDao().getPlayerById(playerId)
    }
}
