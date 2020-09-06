package petrov.ivan.gb65apps.ui.listEmployee.features

import dagger.Component
import petrov.ivan.gb65apps.ui.adapters.EmployeeAdapter

@Component(modules = arrayOf(FragmentListEmployeeModule::class))
@FragmentListEmployeeScope
interface FragmentListEmployeeComponent {
    fun getEmployeeAdapter(): EmployeeAdapter
}