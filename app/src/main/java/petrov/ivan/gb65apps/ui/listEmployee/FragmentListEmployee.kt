package petrov.ivan.gb65apps.ui.listEmployee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import petrov.ivan.gb65apps.R
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.databinding.FragmentListEmployeeBinding
import petrov.ivan.gb65apps.service.ApiService
import petrov.ivan.gb65apps.ui.adapters.EmployeeAdapter
import petrov.ivan.gb65apps.ui.adapters.SpecialityAdapter
import petrov.ivan.gb65apps.ui.base.BaseFragmentViewModel
import petrov.ivan.gb65apps.ui.listEmployee.features.DaggerFragmentListEmployeeComponent
import petrov.ivan.gb65apps.ui.listEmployee.features.FragmentListEmployeeComponent
import petrov.ivan.gb65apps.ui.listEmployee.features.FragmentListEmployeeModule
import timber.log.Timber


class FragmentListEmployee: BaseFragmentViewModel() {
    private lateinit var speciality: Speciality
    private lateinit var binding: FragmentListEmployeeBinding
    private lateinit var viewModel: ListEmployeeViewModel
    private val itemClickListener = EmployeeAdapter.EmployeeClickListener { employee ->
        saveScrollPosition()
        this.findNavController().navigate(
            FragmentListEmployeeDirections.actionFragmentListEmployeeToFragmentEmployeeInfo(employee)
        )
    }
    private val fragmentListEmployeeComponent: FragmentListEmployeeComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentListEmployeeComponent.builder()
            .fragmentListEmployeeModule(FragmentListEmployeeModule(itemClickListener))
            .build()
    }
    private val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.NONE) {
        applicationComponents.getApiService()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_employee, container, false)

        arguments?.let {
            speciality = FragmentListEmployeeArgs.fromBundle(it).speciality
        } ?: Timber.e("incorrect state: arguments not found")

        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = ListEmployeeViewModelFactory(apiService, speciality, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListEmployeeViewModel::class.java)

        binding.viewModel = viewModel
    }

    override fun registerObservers() {
        viewModel.let {
            it.eventLoadComplete.observe(this, Observer { value ->
                if (value) onLoadDataComplete()
            })

            it.eventErrorLoadData.observe(this, Observer { value ->
                if (value) {
                    setRefreshShowing(false)
                    Snackbar.make(binding.root, R.string.error_load_data, Snackbar.LENGTH_LONG).show()
                    viewModel.eventErrorLoadData.value = false
                }
            })

            it.listEmployee.observe(this, Observer {
                (binding.recyclerView.adapter as EmployeeAdapter).items = ArrayList(it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener() {
            viewModel.recyclerViewPosition.value = 0
            loadData()
        }

        binding.recyclerView.adapter = fragmentListEmployeeComponent.getEmployeeAdapter()
        viewModel.listEmployee.value ?:  run {
            loadData()
        }
    }

    private fun loadData() {
        setRefreshShowing(true)
        viewModel.loadData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveScrollPosition()
    }

    private fun onLoadDataComplete() {
        restoreScrollPostion()
        setRefreshShowing(false)
    }

    private fun setRefreshShowing(isShowing: Boolean) {
        binding.swipeRefreshLayout.setRefreshing(isShowing)
    }

    private fun saveScrollPosition() {
        if (isAdded) {
            viewModel.recyclerViewPosition.value =
                (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }
    }

    private fun restoreScrollPostion() {
        viewModel.recyclerViewPosition.value?.let { position ->
            binding.recyclerView.layoutManager?.scrollToPosition(position)
        }
    }
}