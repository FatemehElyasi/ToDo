package ir.fatemehelyasi.todogit.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.fatemehelyasi.todogit.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDo_table ORDER BY id Asc")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)
}