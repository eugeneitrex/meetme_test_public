package by.test.meetmetest.net

import by.test.meetmetest.net.response.GameResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("games.json")
    fun getGames(): Observable<GameResponse>
}
