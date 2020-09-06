package petrov.ivan.gb65apps.service.protocol

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import petrov.ivan.gb65apps.webapi.ParamRespEmployees
import retrofit2.http.GET

interface RestApi{
    @GET("testTask.json")
    fun getEmployees(): Observable<ParamRespEmployees>
}