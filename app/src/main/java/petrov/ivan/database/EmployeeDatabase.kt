package petrov.ivan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBEmployee::class], version = 1,  exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract val employeeDatabaseDao: EmployeeDatabaseDao

    companion object {

        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            EmployeeDatabase::class.java,
            "employee_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}