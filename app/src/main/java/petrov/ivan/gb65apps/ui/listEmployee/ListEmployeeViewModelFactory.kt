package petrov.ivan.gb65apps.ui.listEmployee

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.service.ApiService

class ListEmployeeViewModelFactory(private val apiService: ApiService,
                                   private val speciality: Speciality,
                                   private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListEmployeeViewModel::class.java)) {
            return ListEmployeeViewModel(apiService, speciality, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}