package by.test.meetmetest.ui.top.topplayed

import androidx.fragment.app.viewModels
import by.test.meetmetest.ui.top.TopBaseAdapter
import by.test.meetmetest.ui.top.TopBaseFragment
import by.test.meetmetest.ui.top.TopBaseViewModel

class TopPlayedFragment : TopBaseFragment() {
    override fun getViewModel(): TopBaseViewModel {
        val viewModel: TopPlayedViewModel by viewModels { viewModelFactory }
        return viewModel
    }
    override fun getCustomAdapter(): TopBaseAdapter {
        return TopPlayedAdapter()
    }
}
