package petrov.ivan.gb65apps.ui.listSpeciality

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.gb65apps.service.ApiService

class ListSpecialityViewModelFactory(private val apiService: ApiService,
                                     private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListSpecialityViewModel::class.java)) {
            return ListSpecialityViewModel(apiService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}