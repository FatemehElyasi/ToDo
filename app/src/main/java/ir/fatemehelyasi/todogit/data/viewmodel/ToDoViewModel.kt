package ir.fatemehelyasi.todogit.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ir.fatemehelyasi.todogit.data.ToDoDatabase
import ir.fatemehelyasi.todogit.data.models.ToDoData
import ir.fatemehelyasi.todogit.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//for context
class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    //getting Dao class from database
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val getAllData: LiveData<List<ToDoData>> = repository.getAllData


    //coroutines
    fun insertData(toDoData: ToDoData){
        viewModelScope.launch (  Dispatchers.IO){
           repository.insertData(toDoData)
        }
    }
}