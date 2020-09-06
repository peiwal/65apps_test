package petrov.ivan.gb65apps.modules

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import petrov.ivan.database.EmployeeDatabase
import petrov.ivan.database.EmployeeDatabaseDao
import petrov.ivan.database.EmployeeDatabaseHelper
import petrov.ivan.gb65apps.AppConstants
import petrov.ivan.gb65apps.interfaces.Singleton
import petrov.ivan.gb65apps.service.ApiService
import petrov.ivan.gb65apps.service.protocol.RestApi
import petrov.ivan.gb65apps.utils.ApiConverter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module(includes = arrayOf(OkHttpClientModule::class, ContextModule::class))
class ApplicationModule {

    @Singleton
    @Provides
    fun getRestApi(okHttpClient: OkHttpClient, jacksonConverterFactory: JacksonConverterFactory): RestApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(jacksonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(RestApi::class.java)
    }

    @Singleton
    @Provides
    fun jacksonConverterFactory(): JacksonConverterFactory {
        return JacksonConverterFactory.create(objectMapper())
    }

    @Singleton
    @Provides
    fun getApiService(restApi: RestApi, databaseHelper: EmployeeDatabaseHelper): ApiService {
        return ApiService(restApi, getApiJsonConverter(), databaseHelper)
    }


    @Singleton
    @Provides
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }

    @Singleton
    @Provides
    fun getApiJsonConverter(): ApiConverter {
        return ApiConverter()
    }

    @Singleton
    @Provides
    fun getEmployeeDatabaseDao(context: Context): EmployeeDatabaseDao {
        return EmployeeDatabase.invoke(context).employeeDatabaseDao
    }

    @Singleton
    @Provides
    fun getEmployeeDatabaseHelper(employeeDatabaseDao: EmployeeDatabaseDao): EmployeeDatabaseHelper {
        return EmployeeDatabaseHelper(employeeDatabaseDao, objectMapper())
    }
}