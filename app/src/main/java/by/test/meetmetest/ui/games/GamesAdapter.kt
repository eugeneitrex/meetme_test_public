package by.test.meetmetest.ui.games

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import by.test.meetmetest.R
import by.test.meetmetest.databinding.GameItemLayoutBinding
import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.utils.Constants.Companion.FIRST_PLAYER
import by.test.meetmetest.utils.Constants.Companion.SECOND_PLAYER
import by.test.meetmetest.utils.DateUtils

class GamesAdapter(
    context: Context,
    private val itemClickListener: (Game, View) -> Unit
) : RecyclerView.Adapter<MyGameViewHolder>() {
    private var items = ArrayList<Game>()
    private var regularTextStyle: Typeface? =
        ResourcesCompat.getFont(context, R.font.raleway_regular)
    private var boldTextStyle: Typeface? =
        ResourcesCompat.getFont(context, R.font.raleway_bold)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGameViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.game_item_layout, parent, false)
        return MyGameViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyGameViewHolder, position: Int) {
        setGame(items[position], holder)
    }

    fun setData(list: MutableList<Game>?) {
        list?.let { it ->
            items.clear()
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    private fun setGame(item: Game, holder: MyGameViewHolder) {
        with(holder.binding) {
            gameDate.text = DateUtils.getDate(item.date)
            gameTime.text = DateUtils.getTime(item.date)

            //1st player
            gamePlayerName1.text = item.players[FIRST_PLAYER].name
            gameScore1.text = item.players[FIRST_PLAYER].score.toString()

            //2nd player
            gamePlayerName2.text = item.players[SECOND_PLAYER].name
            gameScore2.text = item.players[SECOND_PLAYER].score.toString()

            setItemTextAppearance(this, item.players)

            gameEdit.setOnClickListener {
                itemClickListener.invoke(item, gameEdit)
            }
        }
    }

    private fun setItemTextAppearance(
        binding: GameItemLayoutBinding,
        players: List<Player>
    ) {
        //bold appearance (win - bold)
        with(binding) {
            gamePlayerName1.typeface = getTypeface(players[FIRST_PLAYER].isWinner)
            gamePlayerName2.typeface = getTypeface(players[SECOND_PLAYER].isWinner)
            gameScore1.typeface = getTypeface(players[FIRST_PLAYER].isWinner)
            gameScore2.typeface = getTypeface(players[SECOND_PLAYER].isWinner)
        }
    }

    private fun getTypeface(isWinner: Boolean) = if (isWinner) boldTextStyle else regularTextStyle
}

class MyGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding = GameItemLayoutBinding.bind(view)
}
