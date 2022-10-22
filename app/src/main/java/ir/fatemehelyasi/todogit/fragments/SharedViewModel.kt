package ir.fatemehelyasi.todogit.fragments

import android.app.Application
import android.content.Context
import android.graphics.Color.blue
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

    //--------------------02
    //checking that are they empty or not
    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    //--------------------04
    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }
            "MEDIUM Priority" -> {
                Priority.MEDIUM
            }
            "LOW Priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }
    //----------------------------------------------------------------------------
     fun parsePriorityToInt(priority: Priority):Int{
        return when(priority){
            Priority.HIGH-> 0
            Priority.MEDIUM-> 1
            Priority.LOW-> 2
        }
    }
    //----------------------------------------------------------------------------

}