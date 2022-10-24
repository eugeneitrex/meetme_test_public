package by.test.meetmetest.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.PopupMenu
import by.test.meetmetest.R
import by.test.meetmetest.data.entities.Player

class DialogUtils {
    companion object {
        /**
         * showing list of names (of players)
         */
        fun showListOfPlayers(
            list: List<Player>,
            context: Context?,
            layoutInflater: LayoutInflater,
            function: (Player) -> Unit
        ) {
            val names = arrayListOf<String>()
            val listArray: ArrayList<Player> = ArrayList(list)
            for (player in list) {
                names.add(player.name)
            }
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val rowList: View = layoutInflater.inflate(R.layout.raw, null)

            val listView = rowList.findViewById<ListView>(R.id.namesListView)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    names
                )
            }
            listView.adapter = adapter

            adapter?.notifyDataSetChanged()

            alertDialog.setView(rowList)
            val dialog: AlertDialog = alertDialog.create()

            listView.setOnItemClickListener { _, _, position, _ ->
                function.invoke(listArray[position])
                dialog.dismiss()
            }

            dialog.show()
        }

        /**
         * Menu when edit/delete game
         */
        fun showPopup(view: View?, edit: () -> Unit, delete: () -> Unit) {
            view?.context?.let {
                val popup = PopupMenu(it, view)
                popup.inflate(R.menu.game_item_menu)

                popup.setOnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.game_edit -> {
                            edit.invoke()
                        }
                        R.id.game_delete -> {
                            delete.invoke()
                        }
                    }

                    true
                }
                popup.show()
            }
        }
    }
}
