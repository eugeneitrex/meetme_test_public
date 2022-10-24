package by.test.meetmetest.ui.top.topwinners

import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.repo.Repository
import by.test.meetmetest.ui.top.TopBaseViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

open class TopWinnersViewModel @Inject constructor(private val repository: Repository) : TopBaseViewModel() {
    override fun getSingleMethod(): Observable<List<Player>> {
        return repository.getTopWinPlayers()
    }
}
