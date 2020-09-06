package petrov.ivan.database

import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Observable
import petrov.ivan.gb65apps.webapi.ParamEmployee
import kotlin.collections.ArrayList

class EmployeeDatabaseHelper(val databaseDao: EmployeeDatabaseDao, val objectMapper: ObjectMapper) {
    fun deleteAll() {
        databaseDao.deleteAll()
    }

    fun insert(obj: ParamEmployee) {
        databaseDao.insert(DBEmployee(objectMapper.writeValueAsString(obj)))
    }

    fun getAllRecords(): Observable<List<ParamEmployee>> {
        val result = ArrayList<ParamEmployee>()
        return databaseDao.getAllRecords().map {
            it.forEach {
                result.add(objectMapper.readValue(it.serializableObj, ParamEmployee::class.java))
            }
            result
        }
    }
}