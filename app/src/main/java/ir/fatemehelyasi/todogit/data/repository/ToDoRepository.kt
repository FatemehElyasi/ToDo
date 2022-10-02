package ir.fatemehelyasi.todogit.data.repository

import androidx.lifecycle.LiveData
import ir.fatemehelyasi.todogit.data.ToDoDao
import ir.fatemehelyasi.todogit.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData:LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

}