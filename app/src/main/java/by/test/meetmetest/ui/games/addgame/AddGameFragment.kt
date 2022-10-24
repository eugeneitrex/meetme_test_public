package by.test.meetmetest.ui.games.addgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.databinding.FragmentAddGameBinding
import by.test.meetmetest.ui.BaseFragment
import by.test.meetmetest.utils.DialogUtils
import by.test.meetmetest.utils.NavigationCommand
import javax.inject.Inject

open class AddGameFragment @Inject constructor() : BaseFragment() {
    private lateinit var binding: FragmentAddGameBinding
    open lateinit var addGameViewModel: AddGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addGameViewModel = getViewModel()
        binding = FragmentAddGameBinding.inflate(inflater, container, false).apply {
            viewModel = addGameViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        addGameViewModel.apply {
            navigationCommand.observeSingleEvent(viewLifecycleOwner) { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions)
                    is NavigationCommand.ToId -> findNavController().navigate(
                        command.navigationId,
                        command.bundle
                    )
                }
            }
            actionLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is AddGameViewModel.Action.ShowError -> {
                        Toast.makeText(context, getString(it.errorStringId), Toast.LENGTH_SHORT)
                            .show()
                    }
                    is AddGameViewModel.Action.NavigateBack -> {
                        activity?.onBackPressed()
                    }
                    is AddGameViewModel.Action.ShowListOfPlayers -> {
                        showListOFPlayers(it.list, it.isFirstPlayer)
                    }
                }
            }
        }

        return binding.root
    }

    open fun getViewModel(): AddGameViewModel {
        val viewModel: AddGameViewModel by viewModels { viewModelFactory }
        return viewModel
    }

    /**
     * show simple list of names (players)
     */
    private fun showListOFPlayers(list: List<Player>, firstPlayer: Boolean) {
        DialogUtils.showListOfPlayers(list, context, layoutInflater) {
            addGameViewModel.playerSelected(it, firstPlayer)
        }
    }
}
