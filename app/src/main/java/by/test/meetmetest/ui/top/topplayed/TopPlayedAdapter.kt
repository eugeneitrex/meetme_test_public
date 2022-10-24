package by.test.meetmetest.ui.top.topplayed

import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.ui.top.TopBaseAdapter

class TopPlayedAdapter : TopBaseAdapter() {
    override fun getValueForPlayer(player: Player) = player.totalGames.toString()
}