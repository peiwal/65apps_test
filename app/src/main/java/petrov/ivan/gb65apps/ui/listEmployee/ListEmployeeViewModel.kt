package petrov.ivan.gb65apps.ui.listEmployee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import petrov.ivan.gb65apps.data.Employee
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.service.ApiService

class ListEmployeeViewModel(private val apiSerivice: ApiService, val speciality: Speciality, application: Application) : AndroidViewModel(application) {
    val recyclerViewPosition = MutableLiveData<Int>()
    val listEmployee = MutableLiveData<List<Employee>>()
    val eventLoadComplete = MutableLiveData<Boolean>()
    val eventErrorLoadData = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    fun loadData() {
        val dispose = apiSerivice.getEmployeeListBySpeciality(speciality)
            .subscribeOn(Schedulers.io())
            .map { it.sortedBy { it.fName } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listEmployee.value = it
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