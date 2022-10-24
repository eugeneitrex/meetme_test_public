package by.test.meetmetest.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.test.meetmetest.databinding.FragmentGamesBinding
import by.test.meetmetest.ui.BaseFragment
import by.test.meetmetest.utils.DialogUtils
import by.test.meetmetest.utils.NavigationCommand
import javax.inject.Inject

class GamesFragment @Inject constructor() : BaseFragment() {
    private lateinit var gamesAdapter: GamesAdapter
    private lateinit var binding: FragmentGamesBinding
    private val gamesViewModel: GamesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(inflater, container, false)

        initList()

        binding.viewModel = gamesViewModel
        gamesViewModel.apply {
            navigationCommand.observeSingleEvent(viewLifecycleOwner) { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions)
                    is NavigationCommand.ToId -> findNavController().navigate(
                        command.navigationId,
                        command.bundle
                    )
                }
            }

            gamesLiveData.observe(viewLifecycleOwner) {
                gamesAdapter.setData(it)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(gamesViewModel)
    }

    private fun initList() {
        with(binding.listGames) {
            gamesAdapter = GamesAdapter(context) { game, view ->
                DialogUtils.showPopup(view, {
                    //edit
                    gamesViewModel.editGameClicked(game)
                }) {
                    //delete
                    gamesViewModel.deleteGameClicked(game)
                }
            }
            adapter = gamesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}
