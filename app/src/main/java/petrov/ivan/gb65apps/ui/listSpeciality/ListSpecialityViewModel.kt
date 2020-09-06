package petrov.ivan.gb65apps.ui.listSpeciality

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.service.ApiService

class ListSpecialityViewModel(private val apiSerivice: ApiService, application: Application) : AndroidViewModel(application) {
    val recyclerViewPosition = MutableLiveData<Int>()
    val listSpeciality = MutableLiveData<List<Speciality>>()
    val eventLoadComplete = MutableLiveData<Boolean>()
    val eventErrorLoadData = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    fun loadData() {
        val dispose = apiSerivice.getSpecialityList()
            .map { it.sortedBy { it.name } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listSpeciality.value = it
                    eventLoadComplete.value = true
                },
                {
                    eventErrorLoadData.value = true
                })
        compositeDisposable.add(dispose)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}