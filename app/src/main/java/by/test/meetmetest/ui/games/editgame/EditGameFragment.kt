package by.test.meetmetest.ui.games.editgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.test.meetmetest.data.entities.Game
import by.test.meetmetest.ui.games.addgame.AddGameFragment
import by.test.meetmetest.utils.Constants

class EditGameFragment : AddGameFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewRoot = super.onCreateView(inflater, container, savedInstanceState)
        val gameToEdit = arguments?.getSerializable(Constants.GAME_EDIT) as Game
        addGameViewModel.setGameToEdit(gameToEdit)
        return viewRoot
    }

    override fun getViewModel(): EditGameViewModel {
        val viewModel: EditGameViewModel by viewModels { viewModelFactory }
        return viewModel
    }
}
