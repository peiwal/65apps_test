package petrov.ivan.gb65apps.ui.listSpeciality.features

import dagger.Module
import dagger.Provides
import petrov.ivan.gb65apps.ui.adapters.SpecialityAdapter

@Module
class FragmentListSpecialityModule(private val itemClickListener: SpecialityAdapter.SpecialityClickListener) {

    @Provides
    @FragmentListSpecialityScope
    fun specialityAdapter(): SpecialityAdapter {
        return SpecialityAdapter(itemClickListener)
    }
}
