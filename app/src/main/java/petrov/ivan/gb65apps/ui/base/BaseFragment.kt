package petrov.ivan.gb65apps.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import petrov.ivan.gb65apps.R
import petrov.ivan.gb65apps.application.MyApplication
import petrov.ivan.gb65apps.components.ApplicationComponents

abstract class BaseFragment : Fragment() {

    protected lateinit var application: MyApplication

    protected val applicationComponents: ApplicationComponents by lazy(mode = LazyThreadSafetyMode.NONE) {
        application.getApplicationComponent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = requireNotNull(this.activity).application as MyApplication
    }
}