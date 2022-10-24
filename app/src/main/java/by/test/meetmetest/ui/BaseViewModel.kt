package by.test.meetmetest.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import by.test.meetmetest.utils.NavigationCommand
import by.test.meetmetest.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel(),
    DefaultLifecycleObserver {
    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    protected val disposableBag = CompositeDisposable()

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        disposableBag.clear()
    }

    fun navigate(@IdRes navigationId: Int, bundle: Bundle? = null) =
        navigationCommand.postValue(NavigationCommand.ToId(navigationId, bundle))
}
