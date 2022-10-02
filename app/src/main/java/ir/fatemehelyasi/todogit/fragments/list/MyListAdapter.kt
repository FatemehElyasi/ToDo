package ir.fatemehelyasi.todogit.fragments.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.models.Priority
import ir.fatemehelyasi.todogit.data.models.ToDoData
import kotlin.math.log

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Title_Txt = itemView.findViewById<TextView>(R.id.title_txt)!!
        val Description_Txt = itemView.findViewById<TextView>(R.id.description_txt)!!
        val Priority_Indicator = itemView.findViewById<CardView>(R.id.priority_indicator)!!
    }

    //------------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        Log.v("1"," onCreateViewHolder")

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    //------------------------------------------------------------------------------------------------
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Log.v("1","onBindViewHolder")


        holder.Title_Txt.text = dataList[position].title
        holder.Description_Txt.text = dataList[position].description

        when (dataList[position].priority) {
            Priority.HIGH -> {
                holder.Priority_Indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.red
                    )
                )
            }
            Priority.LOW -> {
                holder.Priority_Indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.green
                    )
                )
            }
            Priority.MEDIUM -> {
                holder.Priority_Indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.yellow
                    )
                )
            }
        }
    }

    //------------------------------------------------------------------------------------------------
    override fun getItemCount(): Int {
        return dataList.size
    }

    //**************************************************************************************
    @SuppressLint("NotifyDataSetChanged")
    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}