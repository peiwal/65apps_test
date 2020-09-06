package petrov.ivan.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface EmployeeDatabaseDao {
    @Insert
    fun insert(employee: DBEmployee)

    @Query("DELETE FROM employee")
    fun deleteAll()

    @Query("SELECT * from employee")
    fun getAllRecords(): Observable<List<DBEmployee>>
}