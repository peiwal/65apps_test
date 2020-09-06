package petrov.ivan.gb65apps.ui.employeeInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.gb65apps.data.Employee

class EmployeeInfoViewModelFactory(private val application: Application,
                                   private val employee: Employee
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeInfoViewModel::class.java)) {
            return EmployeeInfoViewModel(application, employee) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}