package by.test.meetmetest.repo

import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.data.entities.Player
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {
    /**
     * Fetching list of games. From BD if user has already saved it before. From cloud if not
     *
     * @return Single with result of games
     */
    fun getAllGames(): Observable<List<Game>>

    /**
     * Method for returning list of all players
     *
     * @return Single with players
     */
    fun getAllPlayers(): Single<List<Player>>

    /**
     * Deletes game in DB
     *
     * @param game to delete
     *
     * @return Completable with result
     */
    fun deleteGame(game: Game): Completable

    /**
     * Create existing Game with new players
     *
     * @param player1 new player1
     * @param player2 new player2
     *
     * @return Completable result
     */
    fun createGame(player1: Player, player2: Player): Completable

    /**
     * Update existing Game with new players
     *
     * @param player1 new player1
     * @param player2 new player2
     * @param gameForEdit existing game
     *
     * @return Completable result
     */
    fun updateGame(player1: Player, player2: Player, gameForEdit: Game): Completable

    /**
     * Getting Top Win Players
     *
     * @return Single with list of players
     */
    fun getTopWinPlayers(): Observable<List<Player>>

    /**
     * Getting Top Win Players from 'backend'
     *
     * @return Single with list of players
     */
    fun getTopPlayedPlayers(): Observable<List<Player>>
}