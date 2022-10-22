package ir.fatemehelyasi.todogit.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.fatemehelyasi.todogit.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDo_table ORDER BY id Asc")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteData(toDoData: ToDoData)

    @Query("DELETE  FROM ToDo_table")
    suspend fun deleteAll()

}