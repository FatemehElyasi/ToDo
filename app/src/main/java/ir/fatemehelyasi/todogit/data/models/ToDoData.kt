package ir.fatemehelyasi.todogit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.fatemehelyasi.todogit.data.models.Priority

@Entity(tableName = "ToDo_table")
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority: Priority,
    var description:String
)
