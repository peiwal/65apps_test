package petrov.ivan.gb65apps.ui.listSpeciality.features

import dagger.Component
import petrov.ivan.gb65apps.ui.adapters.SpecialityAdapter

@Component(modules = arrayOf(FragmentListSpecialityModule::class))
@FragmentListSpecialityScope
interface FragmentListSpecialityComponent {
    fun getSpecialityAdapter(): SpecialityAdapter
}