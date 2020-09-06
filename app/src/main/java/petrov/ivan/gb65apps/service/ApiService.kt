package petrov.ivan.gb65apps.service

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import petrov.ivan.database.EmployeeDatabaseHelper
import petrov.ivan.gb65apps.data.Employee
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.service.protocol.RestApi
import petrov.ivan.gb65apps.utils.ApiConverter
import petrov.ivan.gb65apps.webapi.ParamEmployee

class ApiService (val restApi: RestApi, val apiConverter: ApiConverter, val databaseHelper: EmployeeDatabaseHelper) {
    private val loadedDataMap = hashMapOf<Speciality, ArrayList<Employee>>()

    private fun getEmployees(): Observable<HashMap<Speciality, ArrayList<Employee>>> {
        return getDataFromServer()
            .map {
                loadedDataMap.clear()

                val listData = apiConverter.convert(it)
                listData.forEach { employee ->
                    // группировка по специальности
                    employee.speciality.forEach { speciality ->
                        val employeeList = loadedDataMap.get(speciality) ?: ArrayList()
                        employeeList.add(employee)
                        loadedDataMap.put(speciality, employeeList)
                    }
                }

                loadedDataMap
            }
    }

    private fun getDataFromServer(): Observable<List<ParamEmployee>> {
        return restApi.getEmployees()
            .subscribeOn(Schedulers.io())
            .map {
                databaseHelper.deleteAll()
                // сохранение в базу данных
                it.specialities.forEach {
                    databaseHelper.insert(it)
                }

                it.specialities
            }
    }

    // todo unused
    private fun getDataFromDatabase(): Observable<List<ParamEmployee>> {
        return databaseHelper.getAllRecords()
    }

    fun getSpecialityList(): Observable<List<Speciality>> {
        return getEmployees().map {
            val result = ArrayList<Speciality>()
            it.forEach {
                result.add(it.key)
            }

            result.toList()
        }
    }

    fun getEmployeeListBySpeciality(speciality: Speciality): Single<List<Employee>> {
        return Single.just(loadedDataMap.get(speciality)?.toList() ?: listOf())
    }
}