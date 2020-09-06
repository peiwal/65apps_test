package petrov.ivan.gb65apps.components

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Component
import petrov.ivan.gb65apps.interfaces.Singleton
import petrov.ivan.gb65apps.modules.ApplicationModule
import petrov.ivan.gb65apps.service.ApiService


@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponents {
    fun getApiService(): ApiService
    fun getObjectMapper(): ObjectMapper
}