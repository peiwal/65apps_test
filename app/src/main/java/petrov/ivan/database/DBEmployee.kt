package petrov.ivan.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class DBEmployee(
    @ColumnInfo(name = "serializable_obj")
    val serializableObj: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idx")
    var idx: Long = 0L
}
