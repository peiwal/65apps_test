package petrov.ivan.gb65apps.ui.listSpeciality

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
import petrov.ivan.gb65apps.databinding.FragmentListSpecialityBinding
import petrov.ivan.gb65apps.service.ApiService
import petrov.ivan.gb65apps.ui.adapters.SpecialityAdapter
import petrov.ivan.gb65apps.ui.base.BaseFragmentViewModel
import petrov.ivan.gb65apps.ui.listSpeciality.features.DaggerFragmentListSpecialityComponent
import petrov.ivan.gb65apps.ui.listSpeciality.features.FragmentListSpecialityComponent
import petrov.ivan.gb65apps.ui.listSpeciality.features.FragmentListSpecialityModule


class FragmentListSpeciality: BaseFragmentViewModel() {

    private lateinit var binding: FragmentListSpecialityBinding
    private lateinit var viewModel: ListSpecialityViewModel
    private val itemClickListener = SpecialityAdapter.SpecialityClickListener { speciality ->
        saveScrollPosition()
        this.findNavController().navigate(
            FragmentListSpecialityDirections.actionFragmentListSpecialityToFragmentListEmplooyes(speciality)
        )
    }
    private val fragmentListSpecialityComponent: FragmentListSpecialityComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentListSpecialityComponent.builder()
            .fragmentListSpecialityModule(FragmentListSpecialityModule(itemClickListener))
            .build()
    }
    private val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.NONE) {
        applicationComponents.getApiService()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_speciality, container, false)

        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = ListSpecialityViewModelFactory(apiService, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListSpecialityViewModel::class.java)

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

            it.listSpeciality.observe(this, Observer {
                (binding.recyclerView.adapter as SpecialityAdapter).items = ArrayList(it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener() {
            viewModel.recyclerViewPosition.value = 0
            loadData()
        }

        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = fragmentListSpecialityComponent.getSpecialityAdapter()
        viewModel.listSpeciality.value ?:  run {
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