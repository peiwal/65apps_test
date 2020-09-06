package petrov.ivan.gb65apps.application

import androidx.multidex.MultiDexApplication
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import petrov.ivan.gb65apps.components.ApplicationComponents
import petrov.ivan.gb65apps.components.DaggerApplicationComponents
import petrov.ivan.gb65apps.modules.ContextModule
import timber.log.Timber
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        installProviderIfNeeded()
        Timber.plant(Timber.DebugTree())
    }

    private fun installProviderIfNeeded() {
        try {
            ProviderInstaller.installIfNeeded(applicationContext)
            val sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            sslContext.createSSLEngine()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
    }

    private val appComponents: ApplicationComponents = DaggerApplicationComponents
        .builder()
        .contextModule(ContextModule(this))
        .build()

    fun getApplicationComponent() : ApplicationComponents {
        return appComponents
    }
}