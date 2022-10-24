package by.test.meetmetest.ui.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.test.meetmetest.R
import by.test.meetmetest.databinding.PlayerItemLayoutBinding
import by.test.meetmetest.data.entities.Player

abstract class TopBaseAdapter : RecyclerView.Adapter<MyPlayerHolder>() {
    private var items = ArrayList<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlayerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.player_item_layout, parent, false)
        return MyPlayerHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyPlayerHolder, position: Int) {
        setPlayer(items[position], holder)
    }

    fun setData(list: ArrayList<Player>?) {
        list?.let { it ->
            items.clear()
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    private fun setPlayer(player: Player, holder: MyPlayerHolder) {
        with(holder.binding) {
            name.text = player.name
            score.text = getValueForPlayer(player)
        }
    }

    abstract fun getValueForPlayer(player: Player): String
}

class MyPlayerHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding = PlayerItemLayoutBinding.bind(view)
}
