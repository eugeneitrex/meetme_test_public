package by.test.meetmetest.ui.top.topplayed

import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.repo.Repository
import by.test.meetmetest.ui.top.TopBaseViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TopPlayedViewModel @Inject constructor(private val repository: Repository) : TopBaseViewModel() {
    override fun getSingleMethod(): Observable<List<Player>> {
        return repository.getTopPlayedPlayers()
    }
}
