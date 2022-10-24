package by.test.meetmetest.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.test.meetmetest.databinding.FragmentPlayersBinding
import by.test.meetmetest.utils.NavigationCommand
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class TopBaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var playersAdapter: TopBaseAdapter
    lateinit var binding: FragmentPlayersBinding
    private lateinit var winsViewModel: TopBaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayersBinding.inflate(inflater, container, false)
        winsViewModel = getViewModel()
        initList()

        winsViewModel.apply {
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
                playersAdapter.setData(it)
            }

            initCall()
        }

        return binding.root
    }

    abstract fun getViewModel(): TopBaseViewModel

    private fun initList() {
        with(binding.listPlayers) {
            playersAdapter = getCustomAdapter()
            adapter = playersAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    abstract fun getCustomAdapter(): TopBaseAdapter
}
