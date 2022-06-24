package com.example.asystentnauczyciela.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.User
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        val downrow:String = currentItem.lastName+"\t\t"+currentItem.start+" - "+currentItem.stop
        holder.itemView.Name_txt.text = currentItem.firstName
        holder.itemView.Date_txt.text = downrow

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToListFragmentParticipants(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.rowLayout.idEditIcon.setOnClickListener{

            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            val action2 = ListFragmentParticipantsDirections
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}
