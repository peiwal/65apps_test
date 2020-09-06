package petrov.ivan.gb65apps.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.gb65apps.interfaces.Singleton

@Module
class ContextModule(internal var context: Context) {

    @Singleton
    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}
