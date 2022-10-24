package by.test.meetmetest.ui.top.topwinners

import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.ui.top.TopBaseAdapter

class TopWinnersAdapter : TopBaseAdapter() {
    override fun getValueForPlayer(player: Player) = player.totalWins.toString()
}