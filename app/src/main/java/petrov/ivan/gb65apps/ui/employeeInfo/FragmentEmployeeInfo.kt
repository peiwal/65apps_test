package petrov.ivan.gb65apps.ui.employeeInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import petrov.ivan.gb65apps.R
import petrov.ivan.gb65apps.data.Employee
import petrov.ivan.gb65apps.databinding.FragmentEmployeeInfoBinding
import petrov.ivan.gb65apps.ui.base.BaseFragmentViewModel
import timber.log.Timber


class FragmentEmployeeInfo : BaseFragmentViewModel() {
    private lateinit var employee: Employee
    private lateinit var employeeInfoViewModel: EmployeeInfoViewModel
    private lateinit var binding: FragmentEmployeeInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_info, container, false)

        arguments?.let {
            employee = FragmentEmployeeInfoArgs.fromBundle(it).employee
        } ?: Timber.e("incorrect state: arguments not found")

        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = EmployeeInfoViewModelFactory(application, employee)

        employeeInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(EmployeeInfoViewModel::class.java)
        binding.viewModel = employeeInfoViewModel
    }

    override fun registerObservers() = Unit
}