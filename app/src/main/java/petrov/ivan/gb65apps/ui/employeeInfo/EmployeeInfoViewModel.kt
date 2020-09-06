package petrov.ivan.gb65apps.ui.employeeInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import petrov.ivan.gb65apps.data.Employee

class EmployeeInfoViewModel(application: Application, employee: Employee) : AndroidViewModel(application) {
    val employee = MutableLiveData<Employee>()

    init {
        this.employee.value = employee
    }
}