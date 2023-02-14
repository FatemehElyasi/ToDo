package ir.fatemehelyasi.todogit.fragments.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.models.Priority
import ir.fatemehelyasi.todogit.data.models.ToDoData

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title_Txt = itemView.findViewById<TextView>(R.id.title_txt)!!
        val descriptionTxt = itemView.findViewById<TextView>(R.id.description_txt)!!
        val Priority_Indicator = itemView.findViewById<CardView>(R.id.priority_indicator)
        val row_background = itemView.findViewById<ViewGroup>(R.id.row_background)

    }


    //------------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        Log.v("1"," onCreateViewHolder")
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false) )
    }

    //------------------------------------------------------------------------------------------------
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.title_Txt.text=dataList[position].title
        holder.descriptionTxt.text = dataList[position].description

        val priority=dataList[position].priority
        when (priority) {
            Priority.HIGH ->
                holder.Priority_Indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.red
                    )
                )

            Priority.LOW ->
                holder.Priority_Indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.green
                    )
                )

            Priority.MEDIUM ->
                holder.Priority_Indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.yellow
                    )
                )
        }

        holder.row_background.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
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
