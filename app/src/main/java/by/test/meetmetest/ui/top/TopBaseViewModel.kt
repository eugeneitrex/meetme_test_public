package by.test.meetmetest.ui.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.test.meetmetest.data.entities.Player
import by.test.meetmetest.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class TopBaseViewModel : BaseViewModel() {
    private val playersLiveData = MutableLiveData<ArrayList<Player>>()
    val gamesLiveData: LiveData<ArrayList<Player>> = playersLiveData

    fun initCall() {
        disposableBag.add(
            getSingleMethod()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    playersLiveData.value = it as ArrayList
                }, {
                    it.printStackTrace()
                })
        )
    }

    abstract fun getSingleMethod(): Observable<List<Player>>
}
