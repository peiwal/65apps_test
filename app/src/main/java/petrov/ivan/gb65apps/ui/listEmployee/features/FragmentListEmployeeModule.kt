package petrov.ivan.gb65apps.ui.listEmployee.features

import dagger.Module
import dagger.Provides
import petrov.ivan.gb65apps.ui.adapters.EmployeeAdapter

@Module
class FragmentListEmployeeModule(private val itemClickListener: EmployeeAdapter.EmployeeClickListener) {

    @Provides
    @FragmentListEmployeeScope
    fun employeeAdapter(): EmployeeAdapter {
        return EmployeeAdapter(itemClickListener)
    }
}
