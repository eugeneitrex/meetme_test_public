package by.test.meetmetest.ui.top.topwinners

import androidx.fragment.app.viewModels
import by.test.meetmetest.ui.top.TopBaseAdapter
import by.test.meetmetest.ui.top.TopBaseFragment
import by.test.meetmetest.ui.top.TopBaseViewModel

class TopWinnersFragment : TopBaseFragment() {
    override fun getViewModel(): TopBaseViewModel {
        val viewModel: TopWinnersViewModel by viewModels { viewModelFactory }
        return viewModel
    }

    override fun getCustomAdapter(): TopBaseAdapter {
        return TopWinnersAdapter()
    }
}
